/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import net.sf.sprockets.content.Managers;

import static android.content.Intent.ACTION_DELETE;
import static android.content.Intent.ACTION_EDIT;
import static android.content.Intent.ACTION_INSERT;

/**
 * Insert, update, or delete content rows in a background thread. The calling intent must set its
 * action as {@link Intent#ACTION_INSERT ACTION_INSERT}, {@link Intent#ACTION_EDIT ACTION_EDIT}, or
 * {@link Intent#ACTION_DELETE ACTION_DELETE}, and its data as the content URI to affect.
 * {@link #EXTRA_VALUES} must be set for inserts and updates. {@link #EXTRA_SELECTION} and
 * {@link #EXTRA_SELECTION_ARGS} may be set for updates and deletes.
 *
 * @since 2.4.0
 */
public class ContentService extends IntentService {
    private static final String TAG = ContentService.class.getSimpleName();

    /**
     * {@link ContentValues} to insert or update.
     */
    public static final String EXTRA_VALUES = "values";

    /**
     * Update or delete filter.
     */
    public static final String EXTRA_SELECTION = "selection";

    /**
     * Update or delete filter arguments.
     */
    public static final String EXTRA_SELECTION_ARGS = "selection_args";

    /**
     * Tag of a notification to cancel. {@link #EXTRA_NOTIFICATION_ID} must also be provided.
     */
    public static final String EXTRA_NOTIFICATION_TAG = "notification_tag";

    /**
     * ID of a notification to cancel.
     */
    public static final String EXTRA_NOTIFICATION_ID = "notification_id";

    /**
     * Get an Intent to insert or update rows of the content.
     *
     * @param action must be {@link Intent#ACTION_INSERT ACTION_INSERT} or
     *               {@link Intent#ACTION_EDIT ACTION_EDIT}
     * @since 4.0.0
     */
    public static Intent newIntent(Context context, String action, Uri data, ContentValues values) {
        return newIntent(context, action, data, values, null, null);
    }

    /**
     * Get an Intent to update the selected rows of the content.
     *
     * @since 4.0.0
     */
    public static Intent newUpdateIntent(Context context, Uri data, ContentValues values,
                                         String selection, String[] selectionArgs) {
        return newIntent(context, ACTION_EDIT, data, values, selection, selectionArgs);
    }

    /**
     * Get an Intent to delete rows of the content.
     *
     * @since 4.0.0
     */
    public static Intent newDeleteIntent(Context context, Uri data) {
        return newIntent(context, ACTION_DELETE, data, null, null, null);
    }

    /**
     * Get an Intent to delete the selected rows of the content.
     *
     * @since 4.0.0
     */
    public static Intent newDeleteIntent(Context context, Uri data, String selection,
                                         String[] selectionArgs) {
        return newIntent(context, ACTION_DELETE, data, null, selection, selectionArgs);
    }

    private static Intent newIntent(Context context, String action, Uri data, ContentValues values,
                                    String selection, String[] selectionArgs) {
        return new Intent(action, data, context, ContentService.class)
                .putExtra(EXTRA_VALUES, values).putExtra(EXTRA_SELECTION, selection)
                .putExtra(EXTRA_SELECTION_ARGS, selectionArgs);
    }

    private final Context mContext = SprocketsComponent.get().context();

    public ContentService() {
        super(TAG);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        cancelNotification(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @VisibleForTesting
    void cancelNotification(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_NOTIFICATION_ID)) {
            Managers.notification(mContext).cancel(intent.getStringExtra(EXTRA_NOTIFICATION_TAG),
                    intent.getIntExtra(EXTRA_NOTIFICATION_ID, 0));
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        Uri uri = intent.getData();
        ContentValues vals = intent.getParcelableExtra(EXTRA_VALUES);
        String sel = intent.getStringExtra(EXTRA_SELECTION);
        String[] args = intent.getStringArrayExtra(EXTRA_SELECTION_ARGS);
        switch (intent.getAction()) {
            case ACTION_INSERT:
                mContext.getContentResolver().insert(uri, vals);
                break;
            case ACTION_EDIT:
                mContext.getContentResolver().update(uri, vals, sel, args);
                break;
            case ACTION_DELETE:
                mContext.getContentResolver().delete(uri, sel, args);
                break;
            default:
                Log.e(TAG, "Intent action is not INSERT, EDIT, OR DELETE");
        }
    }
}
