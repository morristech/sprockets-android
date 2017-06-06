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

package net.sf.sprockets.content;

import android.location.Location;

import net.sf.sprockets.gms.location.LocationProvider;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;
import org.mockito.Mock;

import io.reactivex.Maybe;

import static com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;
import static net.sf.sprockets.content.LocationLoader.DEFAULT_PRIORITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationLoaderTest extends SprocketsTest {
    @Mock private LocationProvider mLocations;
    private final Location mLocation = new Location("test");

    @Test
    public void testDefaultPriority() {
        when(mLocations.getCurrentLocation(anyInt())).thenReturn(Maybe.just(mLocation));
        assertEquals(mLocation, loader().loadInBackground());
        verify(mLocations).getCurrentLocation(eq(DEFAULT_PRIORITY));
    }

    @Test
    public void testSinglePriority() {
        when(mLocations.getCurrentLocation(anyInt())).thenReturn(Maybe.just(mLocation));
        assertEquals(mLocation, loader().priority(PRIORITY_HIGH_ACCURACY).loadInBackground());
        verify(mLocations).getCurrentLocation(eq(PRIORITY_HIGH_ACCURACY));
    }

    @Test
    public void testFirstPriorityReturns() {
        when(mLocations.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY))
                .thenReturn(Maybe.just(mLocation));
        when(mLocations.getCurrentLocation(PRIORITY_HIGH_ACCURACY)).thenReturn(Maybe.never());
        assertEquals(mLocation,
                loader().priorities(PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_HIGH_ACCURACY)
                        .loadInBackground());
        verify(mLocations).getCurrentLocation(eq(PRIORITY_BALANCED_POWER_ACCURACY));
        verify(mLocations).getCurrentLocation(eq(PRIORITY_HIGH_ACCURACY));
    }

    @Test
    public void testSecondPriorityReturns() {
        when(mLocations.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY))
                .thenReturn(Maybe.never());
        when(mLocations.getCurrentLocation(PRIORITY_HIGH_ACCURACY))
                .thenReturn(Maybe.just(mLocation));
        assertEquals(mLocation,
                loader().priorities(PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_HIGH_ACCURACY)
                        .loadInBackground());
        verify(mLocations).getCurrentLocation(eq(PRIORITY_BALANCED_POWER_ACCURACY));
        verify(mLocations).getCurrentLocation(eq(PRIORITY_HIGH_ACCURACY));
    }

    @Test
    public void testCancel() {
        LocationLoader loader = loader();
        when(mLocations.getCurrentLocation(anyInt())).then(invocation -> {
            loader.cancelLoadInBackground();
            return Maybe.never();
        });
        assertNull(loader.loadInBackground());
        verify(mLocations).getCurrentLocation(eq(DEFAULT_PRIORITY));
    }

    private LocationLoader loader() {
        return new LocationLoader(targetContext, mLocations);
    }
}
