/*
 * Copyright 2017 pushbit <pushbit@gmail.com>
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
import android.database.MatrixCursor;
import android.net.Uri;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static android.provider.BaseColumns._ID;
import static net.sf.sprockets.content.Content.GROUP_BY;
import static net.sf.sprockets.content.Content.HAVING;
import static net.sf.sprockets.content.Content.LIMIT;
import static net.sf.sprockets.content.Content.NOTIFY_CHANGE;
import static org.junit.Assert.assertEquals;

public class UrisTest extends SprocketsTest {
    private static final Uri BASE = Uri.parse("content://com.example/test");

    @Test
    public void testAppendId() {
        int id = 5;
        MatrixCursor c = new MatrixCursor(new String[]{_ID}, 1);
        c.newRow().add(id);
        c.moveToFirst();
        assertEquals(ContentUris.withAppendedId(BASE, id), Uris.appendId(BASE, c));
    }

    @Test
    public void testGroupByHavingLimit() {
        String groupBy = "one";
        String having = "two = ?";
        String limit = "5";
        assertEquals(BASE.buildUpon().appendQueryParameter(GROUP_BY, groupBy)
                        .appendQueryParameter(HAVING, having).appendQueryParameter(LIMIT, limit).build(),
                Uris.groupByHavingLimit(BASE, groupBy, having, limit));
    }

    @Test
    public void testNotifyChange() {
        int id = 5;
        assertEquals(BASE.buildUpon().appendQueryParameter(NOTIFY_CHANGE, "1").build(),
                Uris.notifyChange(BASE, true));
        assertEquals(
                ContentUris.appendId(BASE.buildUpon(), id).appendQueryParameter(NOTIFY_CHANGE, "0")
                        .build(), Uris.notifyChange(BASE, id, false));
    }

    @Test
    public void testMailto() {
        assertEquals(Uri.parse("mailto:alias@example.com?subject=test%20subject&body=test%20body"),
                Uris.mailto(Collections.singletonList("alias@example.com"), null, null,
                        "test subject", "test body"));
        assertEquals(Uri.parse("mailto:alias1@example.com,alias2@example.com" +
                        "?cc=alias-cc1@example.com,alias-cc2@example.com" +
                        "&bcc=alias-bcc1@example.com,alias-bcc2@example.com" +
                        "&subject=test%20subject&body=test%20body"),
                Uris.mailto(Arrays.asList("alias1@example.com", "alias2@example.com"),
                        Arrays.asList("alias-cc1@example.com", "alias-cc2@example.com"),
                        Arrays.asList("alias-bcc1@example.com", "alias-bcc2@example.com"),
                        "test subject", "test body"));
    }
}
