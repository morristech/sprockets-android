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
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Logs app events and sets user properties.
 *
 * @see FirebaseAnalytics
 * @since 4.0.0
 */
public interface Analytics {
    Task<String> getAppInstanceId();

    void setAnalyticsCollectionEnabled(boolean enabled);

    void setMinimumSessionDuration(long milliseconds);

    void setSessionTimeoutDuration(long milliseconds);

    void setCurrentScreen(Activity a, String screenName, String screenClassOverride);

    void logEvent(String name, Bundle params);

    void setUserId(String id);

    void setUserProperty(String name, String value);
}
