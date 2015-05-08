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

package net.sf.sprockets.net;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.google.common.base.Joiner;

import net.sf.sprockets.content.Content;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static net.sf.sprockets.content.Content.CALLER_IS_SYNCADAPTER;
import static net.sf.sprockets.content.Content.GROUP_BY;
import static net.sf.sprockets.content.Content.HAVING;
import static net.sf.sprockets.content.Content.LIMIT;
import static net.sf.sprockets.content.Content.NOTIFY_CHANGE;

/**
 * Utility methods for working with Uris.
 */
public class Uris {
    private Uris() {
    }

    /**
     * Append the cursor's {@link BaseColumns#_ID _ID} value to the URI.
     */
    public static Uri appendId(Uri uri, Cursor cursor) {
        return ContentUris.withAppendedId(uri, cursor.getLong(cursor.getColumnIndex(_ID)));
    }

    /**
     * Append a {@link Content#GROUP_BY group_by} query parameter to the URI.
     *
     * @since 2.4.0
     */
    public static Uri groupBy(Uri uri, String groupBy) {
        return groupByHavingLimit(uri, groupBy, null, null);
    }

    /**
     * Append {@link Content#GROUP_BY group_by} and {@link Content#HAVING having} query parameters
     * to the URI.
     *
     * @since 2.4.0
     */
    public static Uri groupByHaving(Uri uri, String groupBy, String having) {
        return groupByHavingLimit(uri, groupBy, having, null);
    }

    /**
     * Append a {@link Content#LIMIT limit} query parameter to the URI.
     *
     * @since 2.4.0
     */
    public static Uri limit(Uri uri, int limit) {
        return limit(uri, String.valueOf(limit));
    }

    /**
     * Append a {@link Content#LIMIT limit} query parameter to the URI.
     */
    public static Uri limit(Uri uri, String limit) {
        return groupByHavingLimit(uri, null, null, limit);
    }

    /**
     * Append {@link Content#GROUP_BY group_by}, {@link Content#HAVING having}, and
     * {@link Content#LIMIT limit} query parameters to the URI. Any null or empty parameters are
     * skipped.
     *
     * @since 2.4.0
     */
    public static Uri groupByHavingLimit(Uri uri, String groupBy, String having, String limit) {
        Builder builder = uri.buildUpon();
        if (!TextUtils.isEmpty(groupBy)) {
            builder.appendQueryParameter(GROUP_BY, groupBy);
        }
        if (!TextUtils.isEmpty(having)) {
            builder.appendQueryParameter(HAVING, having);
        }
        if (!TextUtils.isEmpty(limit)) {
            builder.appendQueryParameter(LIMIT, limit);
        }
        return builder.build();
    }

    /**
     * Append a {@link Content#NOTIFY_CHANGE notify_change} query parameter to the URI.
     *
     * @since 2.1.0
     */
    public static Uri notifyChange(Uri uri, boolean notify) {
        return notifyChange(uri, 0L, notify);
    }

    /**
     * Append the ID, if it is greater than zero, and a {@link Content#NOTIFY_CHANGE notify_change}
     * query parameter to the URI.
     *
     * @since 2.1.0
     */
    public static Uri notifyChange(Uri uri, long id, boolean notify) {
        Builder builder = uri.buildUpon();
        if (id > 0) {
            ContentUris.appendId(builder, id);
        }
        return builder.appendQueryParameter(NOTIFY_CHANGE, notify ? "1" : "0").build();
    }

    /**
     * Append a {@link Content#CALLER_IS_SYNCADAPTER caller_is_syncadapter} query parameter to the
     * URI.
     */
    public static Uri callerIsSyncAdapter(Uri uri) {
        return uri.buildUpon().appendQueryParameter(CALLER_IS_SYNCADAPTER, "1").build();
    }

    /**
     * Get a {@code mailto} Uri with the headers. Any null or empty parameters are skipped. The
     * subject and body will be encoded.
     */
    public static Uri mailto(List<String> to, List<String> cc, List<String> bcc, String subject,
                             String body) {
        String encSubject = Uri.encode(subject);
        String encBody = Uri.encode(body);
        StringBuilder ssp = new StringBuilder((to != null ? to.size() * 34 : 0)
                + (cc != null ? cc.size() * 34 : 0) + (bcc != null ? bcc.size() * 34 : 0)
                + (encSubject != null ? encSubject.length() : 0)
                + (encBody != null ? encBody.length() : 0));
        Joiner joiner = Joiner.on(',');
        if (to != null && !to.isEmpty()) {
            joiner.appendTo(ssp, to);
        }
        boolean start = true;
        if (cc != null && !cc.isEmpty()) {
            joiner.appendTo(ssp.append(separator(start)).append("cc="), cc);
            start = false;
        }
        if (bcc != null && !bcc.isEmpty()) {
            joiner.appendTo(ssp.append(separator(start)).append("bcc="), bcc);
            start = false;
        }
        if (!TextUtils.isEmpty(encSubject)) {
            ssp.append(separator(start)).append("subject=").append(encSubject);
            start = false;
        }
        if (!TextUtils.isEmpty(encBody)) {
            ssp.append(separator(start)).append("body=").append(encBody);
            start = false;
        }
        return new Builder().scheme("mailto").encodedOpaquePart(ssp.toString()).build();
    }

    /**
     * Get a separator that starts or continues a query string.
     */
    private static char separator(boolean start) {
        return start ? '?' : '&';
    }
}
