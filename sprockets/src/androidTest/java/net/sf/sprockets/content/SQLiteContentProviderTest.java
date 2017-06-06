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

package net.sf.sprockets.content;

import android.app.AppOpsManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.sf.sprockets.database.Cursors;
import net.sf.sprockets.database.EasyCursor;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static android.content.Context.APP_OPS_SERVICE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SQLiteContentProviderTest extends SprocketsTest {
    @Mock(answer = RETURNS_DEEP_STUBS) private Context mContext;
    private SQLiteContentProvider mDb;
    private final Uri TABLE = Uri.parse("content://auth/test");
    private final Uri ROW = ContentUris.withAppendedId(TABLE, 7);
    @Captor private ArgumentCaptor<Uri> mUri;

    @Before
    public void resetBefore() {
        when(mContext.getSystemService(APP_OPS_SERVICE)).thenReturn(mock(AppOpsManager.class));
        mDb = new SQLiteContentProvider() {
            @Override
            protected SQLiteOpenHelper onCreateOpenHelper() {
                return new SQLiteOpenHelper(getContext(), null, null, 1) {
                    @Override
                    public void onCreate(SQLiteDatabase db) {
                        db.execSQL("CREATE TABLE test (one INTEGER PRIMARY KEY, two INTEGER)");
                        ContentValues vals = Values.acquire("one", 3, "two", 5);
                        db.insert("test", null, vals);
                        db.insert("test", null, Values.putAll(vals, "one", 7, "two", 11));
                        db.insert("test", null, Values.putAll(vals, "one", 13, "two", 17));
                        Values.release(vals);
                    }

                    @Override
                    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                    }
                };
            }

            @Override
            @Nullable
            public String getType(@NonNull Uri uri) {
                return null;
            }
        };
        mDb.onCreate();
        mDb.attachInfo(mContext, null);
    }

    @Test
    public void testQuery() {
        assertRow(7, 11, ROW, "two = ?", new String[]{"11"});
    }

    @Test
    public void testInsert() {
        ContentValues vals = Values.acquire("one", 89, "two", 97);
        Uri newRow = mDb.insert(TABLE, vals);
        Values.release(vals);
        assertEquals(ContentUris.withAppendedId(TABLE, 89), newRow);
        assertRow(89, 97, newRow, null, null);
        verifyNotified(newRow);
        assertCount(4);
    }

    @Test
    public void testUpdate() {
        ContentValues vals = Values.acquire("two", 97);
        assertEquals(1, mDb.update(ROW, vals, "two = ?", new String[]{"11"}));
        Values.release(vals);
        assertRow(7, 97, ROW, null, null);
        verifyNotified(ROW);
    }

    @Test
    public void testDelete() {
        assertEquals(1, mDb.delete(ROW, "two = ?", new String[]{"11"}));
        verifyNotified(ROW);
        assertCount(2);
    }

    private void assertRow(int one, int two, Uri uri, String sel, String[] args) {
        EasyCursor c = new EasyCursor(mDb.query(uri, null, sel, args, null));
        assertEquals(1, c.getCount());
        c.moveToFirst();
        assertEquals(one, c.getInt("one"));
        assertEquals(two, c.getInt("two"));
        assertEquals(uri, c.getNotificationUri());
        c.close();
    }

    private void verifyNotified(Uri uri) {
        verify(mContext.getContentResolver()).notifyChange(mUri.capture(), isNull(), eq(true));
        assertEquals(uri, mUri.getValue());
    }

    private void assertCount(int count) {
        assertEquals(count, Cursors.count(mDb.query(TABLE, null, null, null, null)));
    }
}
