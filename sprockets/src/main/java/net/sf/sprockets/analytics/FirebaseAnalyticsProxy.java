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

package net.sf.sprockets.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WAKE_LOCK;

/**
 * Forwards calls to {@link FirebaseAnalytics}.
 *
 * @since 4.0.0
 */
@Singleton
public class FirebaseAnalyticsProxy implements Analytics {
    private final FirebaseAnalytics mAnalytics;

    @Inject
    @RequiresPermission(allOf = {INTERNET, ACCESS_NETWORK_STATE, WAKE_LOCK})
    public FirebaseAnalyticsProxy(Context context) {
        mAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public Task<String> getAppInstanceId() {
        return mAnalytics.getAppInstanceId();
    }

    @Override
    public void setAnalyticsCollectionEnabled(boolean enabled) {
        mAnalytics.setAnalyticsCollectionEnabled(enabled);
    }

    @Override
    public void setMinimumSessionDuration(long milliseconds) {
        mAnalytics.setMinimumSessionDuration(milliseconds);
    }

    @Override
    public void setSessionTimeoutDuration(long milliseconds) {
        mAnalytics.setSessionTimeoutDuration(milliseconds);
    }

    @Override
    public void setCurrentScreen(Activity a, String screenName, String screenClassOverride) {
        mAnalytics.setCurrentScreen(a, screenName, screenClassOverride);
    }

    @Override
    public void logEvent(String name, Bundle params) {
        mAnalytics.logEvent(name, params);
    }

    @Override
    public void setUserId(String id) {
        mAnalytics.setUserId(id);
    }

    @Override
    public void setUserProperty(String name, String value) {
        mAnalytics.setUserProperty(name, value);
    }
}
