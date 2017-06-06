/*
 * Copyright 2013-2016 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.view.animation;

import android.animation.TimeInterpolator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Utility methods for working with Interpolators.
 *
 * @since 4.0.0
 */
public class Interpolators {
    private static AccelerateInterpolator sAccelerate;
    private static AccelerateDecelerateInterpolator sAccelerateDecelerate;
    private static AnticipateInterpolator sAnticipate;
    private static AnticipateOvershootInterpolator sAnticipateOvershoot;
    private static BounceInterpolator sBounce;
    private static DecelerateInterpolator sDecelerate;
    private static FastOutLinearInInterpolator sFastOutLinearIn;
    private static FastOutSlowInInterpolator sFastOutSlowIn;
    private static LinearInterpolator sLinear;
    private static LinearOutSlowInInterpolator sLinearOutSlowIn;
    private static OvershootInterpolator sOvershoot;

    private Interpolators() {
    }

    /**
     * Get a cached instance.
     */
    public static AccelerateInterpolator accelerate() {
        if (sAccelerate == null) {
            sAccelerate = new AccelerateInterpolator();
        }
        return sAccelerate;
    }

    /**
     * Get a cached instance.
     */
    public static AccelerateDecelerateInterpolator accelerateDecelerate() {
        if (sAccelerateDecelerate == null) {
            sAccelerateDecelerate = new AccelerateDecelerateInterpolator();
        }
        return sAccelerateDecelerate;
    }

    /**
     * Get a cached instance.
     */
    public static AnticipateInterpolator anticipate() {
        if (sAnticipate == null) {
            sAnticipate = new AnticipateInterpolator();
        }
        return sAnticipate;
    }

    /**
     * Get a cached instance.
     */
    public static AnticipateOvershootInterpolator anticipateOvershoot() {
        if (sAnticipateOvershoot == null) {
            sAnticipateOvershoot = new AnticipateOvershootInterpolator();
        }
        return sAnticipateOvershoot;
    }

    /**
     * Get a cached instance.
     */
    public static BounceInterpolator bounce() {
        if (sBounce == null) {
            sBounce = new BounceInterpolator();
        }
        return sBounce;
    }

    /**
     * Get a cached instance.
     */
    public static DecelerateInterpolator decelerate() {
        if (sDecelerate == null) {
            sDecelerate = new DecelerateInterpolator();
        }
        return sDecelerate;
    }

    /**
     * Get a cached instance.
     */
    public static FastOutLinearInInterpolator fastOutLinearIn() {
        if (sFastOutLinearIn == null) {
            sFastOutLinearIn = new FastOutLinearInInterpolator();
        }
        return sFastOutLinearIn;
    }

    /**
     * Get a cached instance.
     */
    public static FastOutSlowInInterpolator fastOutSlowIn() {
        if (sFastOutSlowIn == null) {
            sFastOutSlowIn = new FastOutSlowInInterpolator();
        }
        return sFastOutSlowIn;
    }

    /**
     * Get a cached instance.
     */
    public static LinearInterpolator linear() {
        if (sLinear == null) {
            sLinear = new LinearInterpolator();
        }
        return sLinear;
    }

    /**
     * Get a cached instance.
     */
    public static LinearOutSlowInInterpolator linearOutSlowIn() {
        if (sLinearOutSlowIn == null) {
            sLinearOutSlowIn = new LinearOutSlowInInterpolator();
        }
        return sLinearOutSlowIn;
    }

    /**
     * Get a cached instance.
     */
    public static OvershootInterpolator overshoot() {
        if (sOvershoot == null) {
            sOvershoot = new OvershootInterpolator();
        }
        return sOvershoot;
    }

    /**
     * Get an appropriate interpolator for an element which is already on screen.
     */
    public static TimeInterpolator changeOnScreen() {
        return fastOutSlowIn();
    }

    /**
     * Get an appropriate interpolator for an element which is entering the screen.
     */
    public static TimeInterpolator enterScreen() {
        return linearOutSlowIn();
    }

    /**
     * Get an appropriate interpolator for an element which is exiting the screen.
     */
    public static TimeInterpolator exitScreen() {
        return fastOutLinearIn();
    }
}
