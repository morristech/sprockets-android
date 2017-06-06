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

package net.sf.sprockets.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;

import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.Sprockets;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import static net.sf.sprockets.preference.Keys.APP_VERSION_CODE;
import static net.sf.sprockets.preference.Keys.APP_VERSION_NAME;

/**
 * Stores the current app version in SharedPreferences.
 *
 * @since 4.0.0
 */
@Singleton
public class SharedPreferencesApplicationVersion implements ApplicationVersion {
    private int mOldCode;
    private String mOldName;
    private int mNewCode;
    private String mNewName;
    private boolean mIsNew;

    @Inject
    public SharedPreferencesApplicationVersion(Context context, @Sprockets EasyPreferences prefs) {
        try {
            /* get previous and current versions */
            mOldCode = prefs.getInt(APP_VERSION_CODE);
            mOldName = prefs.getString(APP_VERSION_NAME);
            PackageInfo info =
                    context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            mNewCode = info.versionCode;
            mNewName = info.versionName;
            mIsNew = mOldCode != mNewCode || !Objects.equals(mOldName, mNewName);
            if (mIsNew) {
                prefs.edit().putInt(APP_VERSION_CODE, mNewCode)
                        .putString(APP_VERSION_NAME, mNewName).apply();
            }
        } catch (NameNotFoundException e) {
            throw new IllegalStateException("application's package could not be found?!", e);
        }
    }

    @Override
    public int getPreviousVersionCode() {
        return mOldCode;
    }

    @Override
    @Nullable
    public String getPreviousVersionName() {
        return mOldName;
    }

    @Override
    public int getVersionCode() {
        return mNewCode;
    }

    @Override
    public String getVersionName() {
        return mNewName;
    }

    @Override
    public boolean isNew() {
        return mIsNew;
    }

    @Override
    public ApplicationVersion resetPreviousVersion() {
        mOldCode = mNewCode;
        mOldName = mNewName;
        mIsNew = false;
        return this;
    }
}
