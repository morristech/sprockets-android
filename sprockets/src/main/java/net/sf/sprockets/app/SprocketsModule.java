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

import android.content.Context;

import net.sf.sprockets.Sprockets;
import net.sf.sprockets.analytics.Analytics;
import net.sf.sprockets.analytics.FirebaseAnalyticsProxy;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.auth.FirebaseAuthProxy;
import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.content.EasySharedPreferences;
import net.sf.sprockets.crash.Crash;
import net.sf.sprockets.crash.FirebaseCrashProxy;
import net.sf.sprockets.gms.location.GoogleLocationProvider;
import net.sf.sprockets.gms.location.LocationProvider;
import net.sf.sprockets.iid.FirebaseInstanceIdProxy;
import net.sf.sprockets.iid.InstanceId;
import net.sf.sprockets.perf.FirebasePerformanceProxy;
import net.sf.sprockets.perf.Performance;
import net.sf.sprockets.provider.SecureSettings;
import net.sf.sprockets.provider.SettingsSecure;
import net.sf.sprockets.util.AndroidLog;
import net.sf.sprockets.util.FirebaseLog;
import net.sf.sprockets.util.Log;

import dagger.Module;
import dagger.Provides;

import static net.sf.sprockets.preference.Keys.PREFERENCES_NAME;

@Module
class SprocketsModule {
    @Provides
    static ApplicationVersion applicationVersion(SharedPreferencesApplicationVersion version) {
        return version;
    }

    @Provides
    static EasyPreferences easyPreferences(EasySharedPreferences preferences) {
        return preferences;
    }

    @Provides
    @Sprockets
    static EasyPreferences sprocketsEasyPreferences(Context context) {
        return new EasySharedPreferences(context, PREFERENCES_NAME);
    }

    @Provides
    static SecureSettings secureSettings(SettingsSecure settings) {
        return settings;
    }

    @Provides
    static Analytics analytics(FirebaseAnalyticsProxy analytics) {
        return analytics;
    }

    @Provides
    static Auth auth(FirebaseAuthProxy auth) {
        return auth;
    }

    @Provides
    static Crash crash(FirebaseCrashProxy crash) {
        return crash;
    }

    @Provides
    static InstanceId instanceId(FirebaseInstanceIdProxy instanceId) {
        return instanceId;
    }

    @Provides
    static LocationProvider locationProvider(GoogleLocationProvider provider) {
        return provider;
    }

    @Provides
    static Performance performance(FirebasePerformanceProxy performance) {
        return performance;
    }

    @Module(includes = SprocketsModule.class)
    static class Debug {
        @Provides
        static Log log(AndroidLog log) {
            return log;
        }
    }

    @Module(includes = SprocketsModule.class)
    static class Release {
        @Provides
        static Log log(FirebaseLog log) {
            return log;
        }
    }
}
