/*
 * Copyright 2013-2017 pushbit <pushbit@gmail.com>
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import net.sf.sprockets.database.Cursors;
import net.sf.sprockets.immutables.value.NamedMutable;

import org.immutables.value.Value.Modifiable;

import static net.sf.sprockets.content.Content.CALLER_IS_SYNCADAPTER;
import static net.sf.sprockets.content.Content.GROUP_BY;
import static net.sf.sprockets.content.Content.HAVING;
import static net.sf.sprockets.content.Content.LIMIT;
import static net.sf.sprockets.content.Content.NOTIFY_CHANGE;

/**
 * ContentProvider with a SQLite database back end that implements all common database operations
 * and notifies observers of changes. Subclasses need only provide a {@link SQLiteOpenHelper} for
 * the database. They may also {@link #translate(Uri) translate} any non-standard URIs to SQL
 * statement elements. URIs may contain {@link Content#GROUP_BY GROUP_BY},
 * {@link Content#HAVING HAVING}, and/or {@link Content#LIMIT LIMIT} query parameters.
 *
 * @since 4.0.0
 */
public abstract class SQLiteContentProvider extends ContentProvider {
    private static final int SELECT = 0;
    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    private SQLiteOpenHelper mHelper;

    /**
     * Create a helper that provides connections to the database. This method will only be called
     * once, when the provider is created.
     */
    protected abstract SQLiteOpenHelper onCreateOpenHelper();

    @Override
    public boolean onCreate() {
        mHelper = onCreateOpenHelper();
        return mHelper != null;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Cursor query(@NonNull Uri uri, @Nullable String[] proj, @Nullable String sel,
                        @Nullable String[] args, @Nullable String order) {
        Sql sql = elements(SELECT, uri, proj, sel, args, order);
        sql.mResult.setNotificationUri(getContext().getContentResolver(),
                sql.notifyUri() != null ? sql.notifyUri() : uri);
        return sql.mResult;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues vals) {
        Sql sql = elements(INSERT, uri, null, null, null, null);
        long id = mHelper.getWritableDatabase().insert(sql.table(), null, vals);
        Uri notify = ContentUris.withAppendedId(sql.notifyUri(), id);
        if (id > 0) {
            notifyChange(notify, uri);
        }
        return notify;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues vals, @Nullable String sel,
                      @Nullable String[] args) {
        return updDel(UPDATE, uri, vals, sel, args);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String sel, @Nullable String[] args) {
        return updDel(DELETE, uri, null, sel, args);
    }

    /**
     * Update or delete records and get the number of rows affected.
     */
    private int updDel(int op, Uri uri, ContentValues vals, String sel, String[] args) {
        /* get the IDs of records that will be affected */
        Sql sql = elements(op, uri, new String[]{"rowid"}, sel, args, null);
        long[] ids = Cursors.allLongs(sql.mResult);
        /* update or delete the records and then notify about any changes */
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int rows = op == UPDATE ? db.update(sql.table(), vals, sql.sel(), sql.args())
                : db.delete(sql.table(), !TextUtils.isEmpty(sql.sel()) ? sql.sel() : "1",
                        sql.args());
        if (rows > 0) {
            for (long id : ids) {
                notifyChange(ContentUris.withAppendedId(sql.notifyUri(), id), uri);
            }
        }
        return rows;
    }

    /**
     * Notify observers of a change at notify URI. This will be a no-op if the original URI
     * specifies {@link Content#NOTIFY_CHANGE NOTIFY_CHANGE} = false/0. The change will be synced
     * to the network if the {@link Content#CALLER_IS_SYNCADAPTER caller is not a sync adapter}.
     */
    @SuppressWarnings("ConstantConditions")
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
     */
    @SuppressWarnings("UnusedParameters")
    protected MutableSql translate(Uri uri) {
        return null;
    }

    /**
     * Get the updated SQL elements for the URI and, when not inserting, a cursor with the query
     * results.
     */
    private Sql elements(int op, Uri uri, String[] proj, String sel, String[] args, String order) {
        MutableSql sql = translate(uri);
        if (sql == null) {
            sql = Sql.create();
        }
        if (sql.table() == null) {
            sql.table(uri.getPathSegments().get(0));
        }
        if (sql.notifyUri() == null && op != SELECT) {
            sql.notifyUri(uri.buildUpon().path(sql.table()).clearQuery().fragment(null).build());
        }
        if (op != INSERT) { // run the query and return the cursor
            String from = sql.join() != null ? sql.table() + ' ' + sql.join() : sql.table();
            if ((sql.sel() == null || sql.args() == null) && uri.getPathSegments().size() == 2) {
                try { // filter on ID if URI in /table/id format
                    long id = ContentUris.parseId(uri);
                    if (id > 0) {
                        if (sql.sel() == null) {
                            sql.sel("rowid = ?");
                        }
                        if (sql.args() == null) {
                            sql.args(String.valueOf(id));
                        }
                    }
                } catch (NumberFormatException e) { // last segment not a number
                }
            }
            if (sel != null) { // append caller values
                sql.sel(DatabaseUtils.concatenateWhere(sql.sel(), sel));
            }
            if (args != null) {
                sql.args(DatabaseUtils.appendSelectionArgs(sql.args(), args));
            }
            String groupBy = uri.getQueryParameter(GROUP_BY);
            if (groupBy != null) { // prefer caller's value
                sql.groupBy(groupBy);
            }
            String having = uri.getQueryParameter(HAVING);
            if (having != null) {
                sql.having(having);
            }
            if (order != null) {
                sql.orderBy(order);
            }
            String limit = uri.getQueryParameter(LIMIT);
            if (limit != null) {
                sql.limit(limit);
            }
            sql.mResult = mHelper.getReadableDatabase()
                    .query(from, proj, sql.sel(), sql.args(), sql.groupBy(), sql.having(),
                            sql.orderBy(), sql.limit());
        }
        return sql;
    }

    /**
     * <p>
     * Elements of a SQL statement that have been derived from a URI. Example usage:
     * </p>
     * <pre>{@code
     * Sql.create().sel("column_name = ?").args(value).orderBy("column_name");
     * }</pre>
     */
    @Modifiable
    @NamedMutable
    public abstract static class Sql {
        Cursor mResult;

        Sql() {
        }

        /**
         * Mutable instance where values can be set.
         *
         * @since 3.0.0
         */
        public static MutableSql create() {
            return new MutableSql();
        }

        /**
         * Only specified if it's different from the first path segment of the URI.
         */
        @Nullable
        public abstract String table();

        /**
         * JOIN clause following the table name for use in SELECT operations.
         */
        @Nullable
        public abstract String join();

        /**
         * WHERE clause with {@code ?} arguments.
         */
        @Nullable
        public abstract String sel();

        /**
         * Arguments for the WHERE clause.
         */
        @Nullable
        public abstract String[] args();

        /**
         * Default GROUP BY clause that will be used if the caller did not provide their own value.
         */
        @Nullable
        public abstract String groupBy();

        /**
         * Default HAVING clause that will be used if the caller did not provide their own value.
         */
        @Nullable
        public abstract String having();

        /**
         * Default ORDER BY clause that will be used if the caller did not provide their own value.
         */
        @Nullable
        public abstract String orderBy();

        /**
         * Default LIMIT clause that will be used if the caller did not provide their own value.
         *
         * @since 2.3.0
         */
        @Nullable
        public abstract String limit();

        /**
         * Only specified if the path should be something other than the table name followed by a
         * rowid segment.
         */
        @Nullable
        public abstract Uri notifyUri();
    }
}
