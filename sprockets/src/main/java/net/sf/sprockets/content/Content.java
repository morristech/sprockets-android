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

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import net.sf.sprockets.util.StringArrays;

import java.util.Arrays;

import static android.content.ContentResolver.SYNC_EXTRAS_EXPEDITED;
import static android.content.ContentResolver.SYNC_EXTRAS_MANUAL;

/**
 * Constants and utility methods for working with ContentResolvers.
 */
public class Content {
    /**
     * URI query parameter for requesting a limited number of rows returned by the query.
     */
    public static final String LIMIT = "limit_offset";

    /**
     * URI query parameter to specify if observers should be notified about the change.  Default is
     * true.
     *
     * @since 2.1.0
     */
    public static final String NOTIFY_CHANGE = "notify_change";

    /**
     * URI query parameter to specify that the caller is a sync adapter and the changes it makes do
     * not need to be synced to the network.
     */
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";

    /**
     * True if data should be downloaded.
     */
    public static final String SYNC_EXTRAS_DOWNLOAD = "download";

    private Content() {
    }

    /**
     * Get the number of rows at the URI.
     *
     * @since 2.3.0
     */
    public static int getCount(Context context, Uri uri) {
        int rows = 0;
        String[] proj = {"COUNT(*)"};
        Cursor c = context.getContentResolver().query(uri, proj, null, null, null);
        if (c.moveToFirst()) {
            rows = c.getInt(0);
        }
        c.close();
        return rows;
    }

    /**
     * Request that a sync starts immediately.
     *
     * @see ContentResolver#requestSync(Account, String, Bundle)
     * @since 2.3.0
     */
    public static void requestSyncNow(Account account, String authority) {
        requestSyncNow(account, authority, null);
    }

    /**
     * Request that a sync starts immediately.
     *
     * @param extras can be null
     * @see ContentResolver#requestSync(Account, String, Bundle)
     */
    public static void requestSyncNow(Account account, String authority, Bundle extras) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBoolean(SYNC_EXTRAS_MANUAL, true);
        extras.putBoolean(SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, authority, extras);
    }

    /**
     * Holds parameters that are used in a
     * {@link ContentResolver#query(Uri, String[], String, String[], String) query}.
     *
     * @since 2.3.0
     */
    public static class Query {
        public Uri uri;
        public String[] proj;
        public String sel;
        public String[] args;
        public String order;

        public Query() {
        }

        public Query(Uri uri, String[] proj, String sel, String[] args, String order) {
            this.uri = uri;
            this.proj = proj;
            this.sel = sel;
            this.args = args;
            this.order = order;
        }

        public Query uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Query proj(String... proj) {
            this.proj = proj;
            return this;
        }

        public Query sel(String sel) {
            this.sel = sel;
            return this;
        }

        public Query args(String... args) {
            this.args = args;
            return this;
        }

        public Query args(int... args) {
            args(StringArrays.from(args));
            return this;
        }

        public Query args(long... args) {
            args(StringArrays.from(args));
            return this;
        }

        public Query order(String order) {
            this.order = order;
            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(uri, Arrays.hashCode(proj), sel, Arrays.hashCode(args), order);
        }

        @Override
        public boolean equals(Object o) {
            if (o != null) {
                if (this == o) {
                    return true;
                } else if (o instanceof Query) {
                    Query q = (Query) o;
                    return Objects.equal(uri, q.uri) && Arrays.equals(proj, q.proj)
                            && Objects.equal(sel, q.sel) && Arrays.equals(args, q.args)
                            && Objects.equal(order, q.order);
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this).add("uri", uri)
                    .add("proj", Arrays.toString(proj)).add("sel", sel)
                    .add("args", Arrays.toString(args)).add("order", order)
                    .omitNullValues().toString();
        }
    }
}
