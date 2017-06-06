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

package net.sf.sprockets.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Utility methods for working with reveal animations.
 *
 * @since 4.0.0
 */
public class Reveals {
    private Reveals() {
    }

    /**
     * Get an Animator that reveals the rectangular View from the circular View.
     *
     * @param circle to start reveal from
     * @param rect   to reveal, probably should already be {@link View#INVISIBLE invisible}
     */
    public static Animator circleToRect(View circle, View rect) {
        return circleRect(circle, rect, true);
    }

    /**
     * Get an Animator that "unreveals" the rectangular View to the circular View.
     *
     * @param circle to end "unreveal" at
     * @param rect   to "unreveal", will be {@link View#INVISIBLE invisible} when the animation
     *               ends
     */
    public static Animator circleFromRect(View circle, View rect) {
        return circleRect(circle, rect, false);
    }

    private static Animator circleRect(View circle, final View rect, boolean reveal) {
        if (reveal) {
            Views.visible(rect);
        }
        float x = Views.getCenterX(circle);
        float y = Views.getCenterY(circle);
        double hypot =
                Math.hypot(Math.max(x, rect.getWidth() - x), Math.max(y, rect.getHeight() - y));
        Animator anim = ViewAnimationUtils.createCircularReveal(rect, Math.round(x), Math.round(y),
                reveal ? circle.getWidth() / 2 : (float) hypot,
                reveal ? (float) hypot : circle.getWidth() / 2);
        if (!reveal) {
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Views.invisible(rect);
                }
            });
        }
        return anim;
    }
}
