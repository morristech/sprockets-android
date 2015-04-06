/*
 * Copyright 2013-2015 pushbit <pushbit@gmail.com>
 *
 * This file is part of Sprockets.
 *
 * Sprockets is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Sprockets is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Sprockets. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.sprockets.content;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import net.sf.sprockets.database.Cursors;
import net.sf.sprockets.database.Operation;
import net.sf.sprockets.database.sqlite.DbOpenHelper;

import java.util.Arrays;

import static net.sf.sprockets.content.Content.CALLER_IS_SYNCADAPTER;
import static net.sf.sprockets.content.Content.LIMIT;
import static net.sf.sprockets.content.Content.NOTIFY_CHANGE;
import static net.sf.sprockets.database.Operation.DELETE;
import static net.sf.sprockets.database.Operation.INSERT;
import static net.sf.sprockets.database.Operation.SELECT;
import static net.sf.sprockets.database.Operation.UPDATE;

/**
 * ContentProvider with a SQLite database back end that implements all common database operations
 * and notifies observers of changes. Subclasses need only provide a {@link SQLiteOpenHelper} for
 * the database. They may also {@link #translate(Uri) translate} any non-standard URIs to SQL
 * statement elements.
 */
public abstract class DbContentProvider extends ContentProvider {
    /**
     * Subclass provided helper for database connections.
     */
    private SQLiteOpenHelper mHelper;

    /**
     * Get a helper that provides connections to the database. This method will only be called once,
     * when the provider is created.
     *
     * @see DbOpenHelper
     */
    protected abstract SQLiteOpenHelper getOpenHelper();

    @Override
    public boolean onCreate() {
        mHelper = getOpenHelper();
        return mHelper != null;
    }

    @Override
    public Cursor query(Uri uri, String[] proj, String sel, String[] args, String order) {
        Sql sql = elements(SELECT, uri, proj, sel, args, order);
        sql.mResult.setNotificationUri(getContext().getContentResolver(),
                sql.mNotify != null ? sql.mNotify : uri);
        return sql.mResult;
    }

    @Override
    public Uri insert(Uri uri, ContentValues vals) {
        Sql sql = elements(INSERT, uri, null, null, null, null);
        long id = mHelper.getWritableDatabase().insert(sql.mTable, null, vals);
        sql.mNotify = ContentUris.withAppendedId(sql.mNotify, id);
        if (id > 0) {
            notifyChange(sql.mNotify, uri);
        }
        return sql.mNotify;
    }

    @Override
    public int update(Uri uri, ContentValues vals, String sel, String[] args) {
        return updDel(UPDATE, uri, vals, sel, args);
    }

    @Override
    public int delete(Uri uri, String sel, String[] args) {
        return updDel(DELETE, uri, null, sel, args);
    }

    /**
     * Update or delete records and get the number of rows affected.
     */
    private int updDel(Operation op, Uri uri, ContentValues vals, String sel, String[] args) {
        /* get the IDs of records that will be affected */
        Sql sql = elements(op, uri, new String[]{"rowid"}, sel, args, null);
        long[] ids = Cursors.allLongs(sql.mResult);
        /* update or delete the records and then notify about any changes */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int rows = op == UPDATE ? db.update(sql.mTable, vals, sql.mSel, sql.mArgs)
                : db.delete(sql.mTable, !TextUtils.isEmpty(sql.mSel) ? sql.mSel : "1", sql.mArgs);
        if (rows > 0) {
            for (long id : ids) {
                notifyChange(ContentUris.withAppendedId(sql.mNotify, id), uri);
            }
        }
        return rows;
    }

    /**
     * Notify observers of a change at notify URI. This will be a no-op if the original URI
     * specifies {@link Content#NOTIFY_CHANGE NOTIFY_CHANGE} = false/0. The change will be synced to
     * the network if the {@link Content#CALLER_IS_SYNCADAPTER caller is not a sync adapter}.
     */
    private void notifyChange(Uri notify, Uri orig) {
        if (orig.getBooleanQueryParameter(NOTIFY_CHANGE, true)) {
            getContext().getContentResolver().notifyChange(notify, null,
                    !orig.getBooleanQueryParameter(CALLER_IS_SYNCADAPTER, false));
        }
    }

    /**
     * Translate the URI to SQL elements. URI path segments, and possibly query parameters, can be
     * translated into the statement table name, selection, arguments, etc. If the URI is in a
     * standard format, such as {@code content://authority/table-name[/id]}, return null (the
     * default implementation) and the table name and rowid filter will automatically be used.
     *
     * @return null if the URI does not need translation
     */
    protected Sql translate(Uri uri) {
        return null;
    }

    /**
     * Get the updated SQL elements for the URI and, when not inserting, a cursor with the query
     * results.
     */
    private Sql elements(Operation op, Uri uri, String[] proj, String sel, String[] args,
                         String order) {
        Sql sql = translate(uri);
        if (sql == null) {
            sql = new Sql();
        }
        if (sql.mTable == null) {
            sql.mTable = uri.getPathSegments().get(0);
        }
        if (sql.mNotify == null && op != SELECT) {
            sql.mNotify = uri.buildUpon().path(sql.mTable).clearQuery().fragment(null).build();
        }
        if (op != INSERT) { // run the query and return the cursor
            String from = sql.mJoin != null ? sql.mTable + ' ' + sql.mJoin : sql.mTable;
            if ((sql.mSel == null || sql.mArgs == null) && uri.getPathSegments().size() == 2) {
                try { // filter on ID if URI in /table/id format
                    long id = ContentUris.parseId(uri);
                    if (id > 0) {
                        if (sql.mSel == null) {
                            sql.mSel = "rowid = ?";
                        }
                        if (sql.mArgs == null) {
                            sql.mArgs = new String[]{String.valueOf(id)};
                        }
                    }
                } catch (NumberFormatException e) { // last segment not a number
                }
            }
            if (sel != null) { // append caller values
                sql.mSel = DatabaseUtils.concatenateWhere(sql.mSel, sel);
            }
            if (args != null) {
                sql.mArgs = DatabaseUtils.appendSelectionArgs(sql.mArgs, args);
            }
            if (order != null) { // prefer caller's value
                sql.mOrderBy = order;
            }
            String limit = uri.getQueryParameter(LIMIT);
            if (limit != null) {
                sql.mLimit = limit;
            }
            sql.mResult = mHelper.getReadableDatabase().query(from, proj, sql.mSel, sql.mArgs,
                    sql.mGroupBy, sql.mHaving, sql.mOrderBy, sql.mLimit);
        }
        return sql;
    }

    /**
     * <p>
     * Elements of a SQL statement that have been derived from a URI. All methods return their
     * instance so that calls can be chained. For example:
     * </p>
     * <pre>{@code
     * Sql sql = new Sql().sel("column_name = ?").args(new String[]{value});
     * }</pre>
     */
    public static class Sql {
        private String mTable;
        private String mJoin;
        private String mSel;
        private String[] mArgs;
        private String mGroupBy;
        private String mHaving;
        private String mOrderBy;
        private String mLimit;
        private Cursor mResult;
        private Uri mNotify;

        /**
         * Only needs to be specified if it's different from the first path segment of the URI.
         */
        public Sql table(String table) {
            mTable = table;
            return this;
        }

        /**
         * JOIN clause following the table name for use in SELECT operations.
         */
        public Sql join(String join) {
            mJoin = join;
            return this;
        }

        /**
         * WHERE clause with {@code ?} arguments.
         */
        public Sql sel(String sel) {
            mSel = sel;
            return this;
        }

        /**
         * Arguments for the WHERE clause.
         */
        public Sql args(String[] args) {
            mArgs = args;
            return this;
        }

        /**
         * Default GROUP BY clause that will be used if the caller did not provide their own value.
         */
        public Sql groupBy(String groupBy) {
            mGroupBy = groupBy;
            return this;
        }

        /**
         * Default HAVING clause that will be used if the caller did not provide their own value.
         */
        public Sql having(String having) {
            mHaving = having;
            return this;
        }

        /**
         * Default ORDER BY clause that will be used if the caller did not provide their own value.
         */
        public Sql orderBy(String orderBy) {
            mOrderBy = orderBy;
            return this;
        }

        /**
         * Default LIMIT clause that will be used if the caller did not provide their own value.
         *
         * @since 2.3.0
         */
        public Sql limit(String limit) {
            mLimit = limit;
            return this;
        }

        /**
         * Only needs to be specified if the path should be something other than the table name
         * followed by a rowid segment.
         */
        public Sql notify(Uri notify) {
            mNotify = notify;
            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(mTable, mJoin, mSel, Arrays.hashCode(mArgs), mGroupBy, mHaving,
                    mOrderBy, mLimit, mNotify);
        }

        @Override
        public boolean equals(Object o) {
            if (o != null) {
                if (this == o) {
                    return true;
                } else if (o instanceof Sql) {
                    Sql s = (Sql) o;
                    return Objects.equal(mTable, s.mTable) && Objects.equal(mJoin, s.mJoin)
                            && Objects.equal(mSel, s.mSel) && Arrays.equals(mArgs, s.mArgs)
                            && Objects.equal(mGroupBy, s.mGroupBy)
                            && Objects.equal(mHaving, s.mHaving)
                            && Objects.equal(mOrderBy, s.mOrderBy)
                            && Objects.equal(mLimit, s.mLimit) && Objects.equal(mNotify, s.mNotify);
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("table", mTable).add("join", mJoin)
                    .add("sel", mSel).add("args", Arrays.toString(mArgs)).add("groupBy", mGroupBy)
                    .add("having", mHaving).add("orderBy", mOrderBy).add("limit", mLimit)
                    .add("notify", mNotify).omitNullValues().toString();
        }
    }
}
