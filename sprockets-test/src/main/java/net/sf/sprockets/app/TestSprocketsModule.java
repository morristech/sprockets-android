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

import android.app.NotificationManager;
import android.content.Context;

import net.sf.sprockets.analytics.Analytics;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.crash.Crash;
import net.sf.sprockets.gms.location.LocationProvider;
import net.sf.sprockets.iid.InstanceId;
import net.sf.sprockets.perf.Performance;
import net.sf.sprockets.provider.SecureSettings;
import net.sf.sprockets.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.NOTIFICATION_SERVICE;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_MOCKS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Module
class TestSprocketsModule {
    @Provides
    @Singleton
    static Context context() {
        Context context = mock(Context.class, RETURNS_DEEP_STUBS);
        when(context.getApplicationContext()).thenReturn(context);
        when(context.getSystemService(NOTIFICATION_SERVICE))
                .thenReturn(mock(NotificationManager.class));
        return context;
    }

    @Provides
    @Singleton
    static ApplicationVersion applicationVersion() {
        return mock(ApplicationVersion.class);
    }

    @Provides
    @Singleton
    static EasyPreferences easyPreferences() {
        return mock(EasyPreferences.class);
    }

    @Provides
    @Singleton
    static SecureSettings secureSettings() {
        return mock(SecureSettings.class);
    }

    @Provides
    @Singleton
    static Analytics analytics() {
        return mock(Analytics.class);
    }

    @Provides
    @Singleton
    static Auth auth() {
        return mock(Auth.class);
    }

    @Provides
    @Singleton
    static Crash crash() {
        return mock(Crash.class);
    }

    @Provides
    @Singleton
    static InstanceId instanceId() {
        return mock(InstanceId.class);
    }

    @Provides
    @Singleton
    static LocationProvider locationProvider() {
        return mock(LocationProvider.class);
    }

    @Provides
    @Singleton
    static Performance performance() {
        return mock(Performance.class, RETURNS_MOCKS);
    }

    @Provides
    @Singleton
    static Log log() {
        return mock(Log.class);
    }
}
