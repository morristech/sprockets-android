/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
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

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import net.sf.sprockets.R;

import static net.sf.sprockets.view.animation.Interpolators.enterScreen;
import static net.sf.sprockets.view.animation.Interpolators.exitScreen;
import static net.sf.sprockets.view.animation.Interpolators.overshoot;

/**
 * Utility methods for working with ViewPropertyAnimators.
 */
public class Animators {
    private Animators() {
    }

    /**
     * Scale in the View to full size.
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleIn(View view) {
        return scale(view, 1.0f, enterScreen(), R.integer.anim_duration_enter);
    }

    /**
     * Scale out the View to zero size.
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleOut(View view) {
        return scale(view, 0.0f, exitScreen(), R.integer.anim_duration_exit);
    }

    private static ViewPropertyAnimator scale(View view, float size, TimeInterpolator interpolator,
                                              @IntegerRes int duration) {
        return view.animate().scaleX(size).scaleY(size).setInterpolator(interpolator)
                .setDuration(view.getResources().getInteger(duration));
    }

    /**
     * Scale out the image, swap in the Drawable resource, and scale it back in.
     * <p>
     * <strong>Note:</strong> The returned animator's {@code endAction} is already set, so don't
     * overwrite it with your own. To perform an action after the image is scaled back in, call
     * {@link #scaleSwap(ImageView, int, Runnable)} instead.
     * </p>
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleSwap(ImageView image, @DrawableRes int drawableId) {
        return scaleSwap(image, drawableId, null);
    }

    /**
     * Scale out the image, swap in the Drawable resource, scale it back in, and run the action.
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleSwap(ImageView image, @DrawableRes int drawableId,
                                                 Runnable endAction) {
        return scale(image, () -> image.setImageResource(drawableId), endAction);
    }

    /**
     * Scale out the ViewSwitcher, show the next View, and scale it back in.
     * <p>
     * <strong>Note:</strong> The returned animator's {@code endAction} is already set, so don't
     * overwrite it with your own. To perform an action after the View is scaled back in, call
     * {@link #scaleShowNext(ViewSwitcher, Runnable)} instead.
     * </p>
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleShowNext(ViewSwitcher view) {
        return scaleShowNext(view, null);
    }

    /**
     * Scale out the ViewSwitcher, show the next View, scale it back in, and run the action.
     *
     * @since 4.0.0
     */
    public static ViewPropertyAnimator scaleShowNext(ViewSwitcher view, Runnable endAction) {
        return scale(view, view::showNext, endAction);
    }

    private static ViewPropertyAnimator scale(View view, Runnable midAction, Runnable endAction) {
        return scaleOut(view).withEndAction(() -> {
            midAction.run();
            scaleIn(view).setInterpolator(overshoot()).withEndAction(endAction);
        });
    }

    /**
     * Scale the Activity down to the rectangle provided by its
     * {@link Intent#getSourceBounds() getIntent().getSourceBounds()}. To see the Activity behind
     * this one, you must include the below attributes[1] in your Activity theme. You may further
     * modify the animator, for example to fade out the Activity by decreasing its
     * {@link ViewPropertyAnimator#alpha(float) alpha} and/or set an
     * {@link ViewPropertyAnimator#withEndAction(Runnable) end action} that finishes the Activity.
     * When finishing the Activity in this way, you may wish to also call
     * {@link Activity#overridePendingTransition(int, int) Activity.overridePendingTransition(0,
     * 0)} in order to prevent the system Activity close animation from running.
     * <p>
     * 1. Activity theme attributes
     * </p>
     * {@code <item name="android:windowBackground">@android:color/transparent</item>}<br/>
     * {@code <item name="android:windowIsTranslucent">true</item>}
     *
     * @return null when the Activity's Intent does not have
     * {@link Intent#getSourceBounds() source bounds}
     * @see ActivityOptions#makeScaleUpAnimation(View, int, int, int, int)
     */
    @Nullable
    public static ViewPropertyAnimator makeScaleDownAnimation(Activity a) {
        Rect to = a.getIntent().getSourceBounds();
        return to != null ? makeScaleDownAnimation(a, to) : null;
    }

    /**
     * Scale the Activity down to the rectangle. To see the Activity behind this one, you must
     * include the below attributes[1] in your Activity theme. You may further modify the animator,
     * for example to fade out the Activity by decreasing its
     * {@link ViewPropertyAnimator#alpha(float) alpha} and/or set an
     * {@link ViewPropertyAnimator#withEndAction(Runnable) end action} that finishes the Activity.
     * When finishing the Activity in this way, you may wish to also call
     * {@link Activity#overridePendingTransition(int, int) Activity.overridePendingTransition(0,
     * 0)} in order to prevent the system Activity close animation from running.
     * <p>
     * 1. Activity theme attributes
     * </p>
     * {@code <item name="android:windowBackground">@android:color/transparent</item>}<br/>
     * {@code <item name="android:windowIsTranslucent">true</item>}
     *
     * @see ActivityOptions#makeScaleUpAnimation(View, int, int, int, int)
     */
    public static ViewPropertyAnimator makeScaleDownAnimation(Activity a, Rect to) {
        View view = a.getWindow().getDecorView();
        Rect frame = Windows.getFrame(a);
        float sx = (float) to.width() / view.getWidth();
        float sy = (float) to.height() / (view.getHeight() - frame.top); // ignore status bar
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        return view.animate().translationX(to.left).translationY(to.top - frame.top * sy).scaleX(sx)
                .scaleY(sy).withLayer();
    }
}
