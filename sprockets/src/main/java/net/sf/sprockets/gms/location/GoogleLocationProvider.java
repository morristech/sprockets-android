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

import android.content.Context;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

import net.sf.sprockets.gms.common.api.GoogleApiClients;
import net.sf.sprockets.os.Loopers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.Observable;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static com.google.android.gms.location.LocationServices.SettingsApi;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Provides locations from Google Play services.
 *
 * @since 4.0.0
 */
@Singleton
public class GoogleLocationProvider implements LocationProvider {
    final GoogleApiClient.Builder mBuilder;

    @Inject
    public GoogleLocationProvider(Context context) {
        mBuilder = new Builder(context.getApplicationContext()).addApi(LocationServices.API);
    }

    @Override
    public Maybe<LocationSettingsResult> checkLocationSettings(int priority) {
        return Maybe.create(emitter -> {
            GoogleApiClient client = mBuilder.build();
            GoogleApiClients.connect(client, hint -> SettingsApi.checkLocationSettings(client,
                    new LocationSettingsRequest.Builder()
                            .addLocationRequest(LocationRequest.create().setPriority(priority))
                            .setAlwaysShow(true).build()).setResultCallback(result -> {
                client.disconnect();
                emitter.onSuccess(result);
            }), result -> emitter.onComplete());
        });
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public Maybe<Location> getLastLocation() {
        return Maybe.create(emitter -> {
            GoogleApiClient client = mBuilder.build();
            GoogleApiClients.connect(client, hint -> {
                Location location = FusedLocationApi.getLastLocation(client);
                client.disconnect();
                if (location != null) {
                    emitter.onSuccess(location);
                } else {
                    emitter.onComplete();
                }
            }, result -> emitter.onComplete());
        });
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public Maybe<Location> getRecentLocation(int maxAge, TimeUnit unit, int priority) {
        return getLastLocation().filter(location ->
                SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos()
                        <= NANOSECONDS.convert(maxAge, unit))
                .switchIfEmpty(getCurrentLocation(priority));
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public Maybe<Location> getCurrentLocation(int priority) {
        return requestLocationUpdates(
                LocationRequest.create().setPriority(priority).setNumUpdates(2).setInterval(0L))
                .elementAt(1L); // first update is "last location", so get second one which is fresh
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public Observable<Location> requestLocationUpdates(LocationRequest request) {
        return Observable.create(emitter -> {
            GoogleApiClient client = mBuilder.build();
            LocationListener listener = emitter::onNext;
            GoogleApiClients.connect(client, hint -> FusedLocationApi
                    .requestLocationUpdates(client, request, listener, Loopers.nullOrMain())
                    .setResultCallback(status -> {
                        if (!status.isSuccess()) {
                            emitter.onComplete();
                        }
                    }), result -> emitter.onComplete());
            emitter.setCancellable(() -> {
                if (client.isConnected()) {
                    FusedLocationApi.removeLocationUpdates(client, listener);
                }
                client.disconnect();
            });
        });
    }
}
