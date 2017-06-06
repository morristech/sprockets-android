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

package net.sf.sprockets.gms.location;

import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Provides recent, current, and ongoing device locations.
 *
 * @see FusedLocationProviderApi
 * @see SettingsApi
 * @since 4.0.0
 */
public interface LocationProvider {
    /**
     * Check if system settings are in the state needed to request locations with the priority.
     *
     * @param priority must be one of the {@link LocationRequest} PRIORITY constants
     */
    Maybe<LocationSettingsResult> checkLocationSettings(int priority);

    /**
     * Get the most recently reported location.
     */
    Maybe<Location> getLastLocation();

    /**
     * Get the most recently reported location if its age is not greater than the provided age.
     * Otherwise get the current location.
     *
     * @param priority must be one of the {@link LocationRequest} PRIORITY constants. Only used
     *                 when getting the current location.
     */
    Maybe<Location> getRecentLocation(int maxAge, TimeUnit unit, int priority);

    /**
     * Get the current location.
     *
     * @param priority must be one of the {@link LocationRequest} PRIORITY constants
     */
    Maybe<Location> getCurrentLocation(int priority);

    /**
     * Get location updates according to the request.
     */
    Observable<Location> requestLocationUpdates(LocationRequest request);
}
