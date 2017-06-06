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

package net.sf.sprockets.remoteconfig;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides a Singleton FirebaseRemoteConfig instance with default, previously activated, and
 * (eventually) newly fetched values. Depends on a {@link RemoteConfigDefaults} binding which you
 * must provide.
 *
 * @since 4.0.0
 */
@Module
public class RemoteConfigModule {
    @Provides
    @Singleton
    static FirebaseRemoteConfig firebaseRemoteConfig(@RemoteConfigDefaults int resourceId) {
        FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
        config.setDefaults(resourceId);
        config.fetch().addOnSuccessListener(result -> config.activateFetched());
        return config;
    }
}
