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

package net.sf.sprockets.app;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import net.sf.sprockets.content.Managers;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import static net.sf.sprockets.app.ContentService.EXTRA_NOTIFICATION_ID;
import static net.sf.sprockets.app.ContentService.EXTRA_NOTIFICATION_TAG;
import static net.sf.sprockets.app.ContentService.EXTRA_SELECTION;
import static net.sf.sprockets.app.ContentService.EXTRA_SELECTION_ARGS;
import static net.sf.sprockets.app.ContentService.EXTRA_VALUES;
import static org.mockito.Mockito.verify;

public class ContentServiceTest extends SprocketsTest {
    private final Context mContext = SprocketsComponent.get().context();
    private final Uri mUri = Uri.EMPTY;
    private final ContentValues mVals = new ContentValues();
    private final String mSel = "sel";
    private final String[] mArgs = {"args"};

    @Test
    public void testCancelNotification() {
        String tag = "tag";
        int id = 5;
        new ContentService().cancelNotification(new Intent().putExtra(EXTRA_NOTIFICATION_TAG, tag)
                .putExtra(EXTRA_NOTIFICATION_ID, id));
        verify(Managers.notification(mContext)).cancel(tag, id);
    }

    @Test
    public void testInsert() {
        // TODO_ uncomment when can mock final methods on Android
        //action(ACTION_INSERT);
        //verify(contentResolver).insert(mUri, mVals);
    }

    @Test
    public void testUpdate() {
        //action(ACTION_EDIT);
        //verify(contentResolver).update(mUri, mVals, mSel, mArgs);
    }

    @Test
    public void testDelete() {
        //action(ACTION_DELETE);
        //verify(contentResolver).delete(mUri, mSel, mArgs);
    }

    private void action(String action) {
        new ContentService().onHandleIntent(new Intent(action, mUri).putExtra(EXTRA_VALUES, mVals)
                .putExtra(EXTRA_SELECTION, mSel).putExtra(EXTRA_SELECTION_ARGS, mArgs));
    }

    @After
    public void resetAfter() {
        Mockito.clearInvocations(mContext);
    }
}
