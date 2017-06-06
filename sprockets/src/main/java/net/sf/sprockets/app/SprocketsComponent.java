/*
 * Copyright 2016-2017 pushbit <pushbit@gmail.com>
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

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import net.sf.sprockets.analytics.Analytics;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.crash.Crash;
import net.sf.sprockets.gms.location.LocationProvider;
import net.sf.sprockets.iid.InstanceId;
import net.sf.sprockets.perf.Performance;
import net.sf.sprockets.provider.SecureSettings;
import net.sf.sprockets.util.Log;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.internal.DoubleCheck;

import static android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides application details and interfaces to external systems.
 *
 * @since 4.0.0
 */
public abstract class SprocketsComponent {
    private static Provider<SprocketsComponent> sProvider;

    /**
     * Called by {@link InitProvider}.
     */
    static void init(Context context) {
        Context app = context.getApplicationContext();
        sProvider = DoubleCheck.provider(
                () -> (app.getApplicationInfo().flags & FLAG_DEBUGGABLE) == FLAG_DEBUGGABLE
                        ? DaggerSprocketsComponent_Debug.builder().context(app).build()
                        : DaggerSprocketsComponent_Release.builder().context(app).build());
    }

    /**
     * Provide an alternate version of this component to callers.
     */
    public static void provider(Provider<SprocketsComponent> provider) {
        sProvider = checkNotNull(provider);
    }

    /**
     * Get the instance of this class. If you may be running in a process other than the main one
     * (such as in {@link Application}), call {@link #get(Context)} instead.
     */
    public static SprocketsComponent get() {
        checkNotNull(sProvider, "not in main process, call get(Context) instead");
        return sProvider.get();
    }

    /**
     * Get the instance of this class, initialising with the context if necessary.
     */
    public static SprocketsComponent get(Context context) {
        if (sProvider == null) {
            init(context);
        }
        return get();
    }

    /**
     * Get the application context.
     */
    public abstract Context context();

    /**
     * Get application version details.
     */
    public abstract ApplicationVersion version();

    /**
     * Get the default SharedPreferences.
     */
    public abstract EasyPreferences preferences();

    /**
     * Get an implementation according to the application's
     * {@link ApplicationInfo#FLAG_DEBUGGABLE debuggable} state. When the application is
     * debuggable, log messages are sent to the Android {@link android.util.Log Log}. When it is
     * not, WARN and higher priority messages are sent to
     * <a href="https://firebase.google.com/docs/crash/" target="_blank">
     * Firebase Crash Reporting</a>.
     */
    public abstract Log log();

    /**
     * Get system settings.
     */
    public abstract SecureSettings secureSettings();

    /**
     * Log app events.
     */
    public abstract Analytics analytics();

    /**
     * Manage user authentication.
     */
    public abstract Auth auth();

    /**
     * Report crashes.
     */
    public abstract Crash crash();

    /**
     * Get app tokens.
     */
    public abstract InstanceId instanceId();

    /**
     * Get device location.
     */
    public abstract LocationProvider locationProvider();

    /**
     * Create performance traces.
     */
    public abstract Performance performance();

    @Component(modules = SprocketsModule.Debug.class)
    @Singleton
    abstract static class Debug extends SprocketsComponent {
        @Component.Builder
        interface Builder {
            @BindsInstance
            Builder context(Context context);

            SprocketsComponent build();
        }
    }

    @Component(modules = SprocketsModule.Release.class)
    @Singleton
    abstract static class Release extends SprocketsComponent {
        @Component.Builder
        interface Builder extends Debug.Builder {
        }
    }
}
