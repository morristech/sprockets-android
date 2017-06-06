/*
 * Copyright 2015-2016 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.transition;

import android.content.Context;
import android.support.annotation.UiThread;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.ViewGroup;

import net.sf.sprockets.R;

/**
 * Utility methods for working with Transitions.
 *
 * @since 4.0.0
 */
public class Transitions {
    private static Transition sDelayedTransition;
    private static boolean sDelayedTransitionRunning;
    private static Transition sArc;

    private Transitions() {
    }

    /**
     * {@link TransitionManager#beginDelayedTransition(ViewGroup) Begin a delayed transition} if
     * one is not already running.
     */
    @UiThread
    public static void tryDelayed(ViewGroup sceneRoot) {
        if (!sceneRoot.isLaidOut()) { // transition won't run
            return;
        }
        if (sDelayedTransition == null) {
            sDelayedTransition = new AutoTransition().addListener(new SimpleTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    sDelayedTransitionRunning = false;
                }
            });
        }
        if (!sDelayedTransitionRunning) {
            sDelayedTransitionRunning = true;
            TransitionManager.beginDelayedTransition(sceneRoot, sDelayedTransition);
        }
    }

    /**
     * Get a cached arc motion transition.
     */
    public static Transition arcMotion(Context context) {
        if (sArc == null) {
            sArc = TransitionInflater.from(context).inflateTransition(
                    R.transition.sprockets_arc_motion);
        }
        return sArc;
    }
}
