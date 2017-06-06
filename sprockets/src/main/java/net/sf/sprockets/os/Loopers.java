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

package net.sf.sprockets.os;

import android.os.Looper;

/**
 * Utility methods for working with Loopers.
 *
 * @since 4.0.0
 */
public class Loopers {
    private Loopers() {
    }

    /**
     * Get this thread's Looper, if it's a Looper thread. Otherwise get the main thread Looper.
     */
    public static Looper mineOrMain() {
        Looper looper = Looper.myLooper();
        return looper != null ? looper : Looper.getMainLooper();
    }

    /**
     * Get null if this thread is a Looper thread. Otherwise get the main thread Looper.
     */
    public static Looper nullOrMain() {
        return Looper.myLooper() != null ? null : Looper.getMainLooper();
    }
}
