/*
 * Copyright 2013-2015 pushbit <pushbit@gmail.com>
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

import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Instances of {@link Interpolator}s.
 */
public class Interpolators {
    public static final AccelerateInterpolator ACCELERATE = new AccelerateInterpolator();
    public static final DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
    public static final AccelerateDecelerateInterpolator ACCEL_DECEL =
            new AccelerateDecelerateInterpolator();
    public static final AnticipateInterpolator ANTICIPATE = new AnticipateInterpolator();
    public static final OvershootInterpolator OVERSHOOT = new OvershootInterpolator();
    public static final AnticipateOvershootInterpolator ANTI_OVER =
            new AnticipateOvershootInterpolator();
    public static final BounceInterpolator BOUNCE = new BounceInterpolator();

    /**
     * 1 cycle.
     */
    public static final CycleInterpolator CYCLE = new CycleInterpolator(1.0f);

    /**
     * @since 2.4.0
     */
    public static final FastOutLinearInInterpolator FAST_OUT_LINEAR_IN =
            new FastOutLinearInInterpolator();

    /**
     * @since 2.4.0
     */
    public static final FastOutSlowInInterpolator FAST_OUT_SLOW_IN =
            new FastOutSlowInInterpolator();

    public static final LinearInterpolator LINEAR = new LinearInterpolator();

    /**
     * @since 2.4.0
     */
    public static final LinearOutSlowInInterpolator LINEAR_OUT_SLOW_IN =
            new LinearOutSlowInInterpolator();

    private Interpolators() {
    }
}
