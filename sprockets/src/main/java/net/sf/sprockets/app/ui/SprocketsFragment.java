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

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat.OnRequestPermissionsResultCallback;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Performs common actions during the Fragment lifecycle, including starting and stopping
 * {@link Presenter}s and forwarding permission request results to {@link EasyPermissions}.
 *
 * @since 4.0.0
 */
public abstract class SprocketsFragment extends Fragment
        implements OnRequestPermissionsResultCallback {
    /**
     * Shortcut to {@link #getActivity()}.
     */
    @Nullable protected Activity a;

    private Presenter mPres;

    /**
     * Start and stop the presenter, which directs this view, on Fragment View create and Fragment
     * destroy.
     */
    protected <T extends Presenter> T manage(T presenter) {
        mPres = presenter;
        return presenter;
    }

    /**
     * Shortcut to {@link #getActivity()}, casting to your assignment type.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Activity> T a() {
        return (T) a;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mPres != null) {
            mPres.onStart(savedInstanceState != null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDestroy() {
        if (mPres != null) {
            mPres.onStop();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        a = null;
        super.onDetach();
    }
}
