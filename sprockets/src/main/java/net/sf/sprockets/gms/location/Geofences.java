/*
 * Copyright 2015 pushbit <pushbit@gmail.com>
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

import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER;
import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT;

/**
 * Constants for working with Geofences.
 *
 * @since 2.4.0
 */
public class Geofences {
    /**
     * Enter, dwell, and exit transitions.
     */
    public static final int GEOFENCE_TRANSITION_ALL =
            GEOFENCE_TRANSITION_ENTER | GEOFENCE_TRANSITION_DWELL | GEOFENCE_TRANSITION_EXIT;

    /**
     * Number of geofences that can be registered per app.
     */
    public static final int MAX_GEOFENCES = 100;

    /**
     * Recommended minimum distance in metres to trigger a geofence event.
     */
    public static final float GEOFENCE_RADIUS = 100.0f;

    private Geofences() {
    }
}
