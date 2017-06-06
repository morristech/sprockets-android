/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.gms.maps;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds.Builder;

import net.sf.sprockets.R;
import net.sf.sprockets.gms.location.LocationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Utility methods for working with GoogleMaps.
 *
 * @since 4.0.0
 */
@Singleton
public class GoogleMaps {
    private final Context mContext;
    private final LocationProvider mProvider;

    @Inject
    public GoogleMaps(Context context, LocationProvider provider) {
        mContext = context;
        mProvider = provider;
    }

    /**
     * Move to the most recently reported location and zoom to the default level.
     */
    public void moveCameraToLastLocation(GoogleMap map) {
        moveCameraToLastLocation(map, 15.0f);
    }

    /**
     * Move and zoom to the most recently reported location.
     *
     * @see CameraUpdateFactory#newLatLngZoom(LatLng, float)
     */
    public void moveCameraToLastLocation(GoogleMap map, float zoom) {
        mProvider.getLastLocation().subscribe(location -> map.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom)));
    }

    /**
     * If the position is not visible on the map, animate the camera to include it.
     */
    public void animateCameraToIncludePosition(GoogleMap map, LatLng position) {
        animateCameraToIncludePosition(map, position, 0L);
    }

    /**
     * If the position is not visible on the map, animate the camera to include it, after the delay.
     */
    public void animateCameraToIncludePosition(GoogleMap map, LatLng position, long delay) {
        if (!map.getProjection().getVisibleRegion().latLngBounds.contains(position)) {
            if (delay > 0) {
                new Handler(Looper.getMainLooper())
                        .postDelayed(() -> doAnimateCameraToIncludePosition(map, position), delay);
            } else {
                doAnimateCameraToIncludePosition(map, position);
            }
        }
    }

    private void doAnimateCameraToIncludePosition(GoogleMap map, LatLng position) {
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(
                new Builder().include(map.getCameraPosition().target).include(position).build(),
                mContext.getResources().getDimensionPixelOffset(R.dimen.touch_target_min_size)));
    }
}
