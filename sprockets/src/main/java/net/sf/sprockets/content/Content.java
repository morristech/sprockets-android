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
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.sf.sprockets.database.Cursors;
import net.sf.sprockets.immutables.value.NamedMutable;
import net.sf.sprockets.util.Elements;

import org.immutables.value.Value.Modifiable;

import static android.content.ContentResolver.SYNC_EXTRAS_EXPEDITED;
import static android.content.ContentResolver.SYNC_EXTRAS_MANUAL;

/**
 * Constants and utility methods for working with ContentResolvers.
 */
public class Content {
    /**
     * URI query parameter for the column(s) to group the rows by.
     *
     * @since 2.4.0
     */
    public static final String GROUP_BY = "group_by";

    /**
     * URI query parameter for the filter to apply after grouping the rows.
     *
     * @since 2.4.0
     */
    public static final String HAVING = "having";

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
     * @return {@link Integer#MIN_VALUE} if the URI could not be queried
     * @since 2.3.0
     */
    public static int getCount(Context context, Uri uri) {
        String[] proj = {"COUNT(*)"};
        return Cursors.firstInt(context.getContentResolver().query(uri, proj, null, null, null));
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
     * @see ContentResolver#requestSync(Account, String, Bundle)
     */
    public static void requestSyncNow(Account account, String authority, @Nullable Bundle extras) {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBoolean(SYNC_EXTRAS_MANUAL, true);
        extras.putBoolean(SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(account, authority, extras);
    }

    /**
     * <p>
     * Holds parameters that are used in a
     * {@link ContentResolver#query(Uri, String[], String, String[], String) query}. Example usage:
     * </p>
     * <pre>{@code
     * Query.create().uri(SOME_URI).proj("column_name").sel("other_column = ?").args(value);
     * }</pre>
     *
     * @since 2.3.0
     */
    @Modifiable
    @NamedMutable
    public abstract static class Query {
        Query() {
        }

        /**
         * Mutable instance where values can be set.
         *
         * @since 3.0.0
         */
        public static MutableQuery create() {
            return new MutableQuery();
        }

        public abstract Uri uri();

        public abstract MutableQuery uri(Uri uri);

        /**
         * Parse the URI.
         *
         * @since 4.0.0
         */
        public MutableQuery uri(String uri) {
            return uri(Uri.parse(uri));
        }

        @Nullable
        public abstract String[] proj();

        @Nullable
        public abstract String sel();

        @Nullable
        public abstract String[] args();

        public abstract MutableQuery args(String... args);

        public MutableQuery args(int... args) {
            return args(Elements.toStrings(args));
        }

        public MutableQuery args(long... args) {
            return args(Elements.toStrings(args));
        }

        @Nullable
        public abstract String order();
    }
}
