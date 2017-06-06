/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayDeque;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Performs common actions during the Activity lifecycle, including starting and stopping
 * {@link Presenter}s and forwarding permission request results to {@link EasyPermissions}.
 *
 * @since 4.0.0
 */
public abstract class SprocketsActivity extends AppCompatActivity {
    private Presenter mPres;
    private boolean mStateSaved;
    private Handler mHandler;
    private ArrayDeque<Runnable> mPosts;

    /**
     * Start and stop the presenter, which directs this view, on Activity create and destroy.
     */
    protected <T extends Presenter> T manage(T presenter) {
        mPres = presenter;
        return presenter;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getParentActivityIntent() != null) {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true); // needed for Toolbars
            }
        }
        if (mPres != null) {
            mPres.onStart(savedInstanceState != null);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mPosts != null) {
            while (!mPosts.isEmpty()) {
                mPosts.poll().run();
            }
        }
        mStateSaved = false;
    }

    /**
     * If {@link #onSaveInstanceState(Bundle) onSaveInstanceState} hasn't been called yet, post the
     * action now. Otherwise save the action and run it when the Activity resumes.
     */
    @UiThread
    public void postWhenResumed(Runnable action) {
        if (!mStateSaved) {
            if (mHandler == null) {
                mHandler = new Handler();
            }
            mHandler.post(action);
        } else {
            if (mPosts == null) {
                mPosts = new ArrayDeque<>();
            }
            mPosts.offer(action);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStateSaved = true;
    }

    @Override
    protected void onDestroy() {
        if (mPres != null) {
            mPres.onStop();
        }
        super.onDestroy();
    }
}
