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

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Utility methods for working with Views.
 */
public class Views {
    private Views() {
    }

    /**
     * Get the View with the ID in the activity's View hierarchy.
     *
     * @return null if a View with the ID doesn't exist in the activity's View hierarchy
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findById(Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * True if the View is not null and is visible.
     *
     * @since 4.0.0
     */
    public static boolean isVisible(@Nullable View view) {
        return is(view, VISIBLE);
    }

    /**
     * True if the View is not null and is invisible.
     *
     * @since 4.0.0
     */
    public static boolean isInvisible(@Nullable View view) {
        return is(view, INVISIBLE);
    }

    /**
     * True if the View is not null and is gone.
     *
     * @since 4.0.0
     */
    public static boolean isGone(@Nullable View view) {
        return is(view, GONE);
    }

    private static boolean is(@Nullable View view, int visibility) {
        return view != null && view.getVisibility() == visibility;
    }

    /**
     * Make the View visible if it isn't already.
     */
    public static <T extends View> T visible(@Nullable T view) {
        return set(view, VISIBLE);
    }

    /**
     * Make the View invisible if it isn't already.
     */
    public static <T extends View> T invisible(@Nullable T view) {
        return set(view, INVISIBLE);
    }

    /**
     * Make the View gone if it isn't already.
     */
    public static <T extends View> T gone(@Nullable T view) {
        return set(view, GONE);
    }

    private static <T extends View> T set(T view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
        return view;
    }

    /**
     * {@link View#measure(int, int) Measure} the View without any parent constraints. You can then
     * call {@link View#getMeasuredWidth()} and {@link View#getMeasuredHeight()}.
     *
     * @param view must have {@link LayoutParams}
     */
    public static <T extends View> T measure(T view) {
        return measure(view, null);
    }

    /**
     * {@link View#measure(int, int) Measure} the View in its parent. You can then call
     * {@link View#getMeasuredWidth()} and {@link View#getMeasuredHeight()}.
     *
     * @param view   must have {@link LayoutParams}
     * @param parent must already be measured
     */
    public static <T extends View> T measure(T view, ViewGroup parent) {
        LayoutParams p = view.getLayoutParams();
        int w = parent != null && p.width == MATCH_PARENT ? parent.getMeasuredWidth() : p.width;
        int h = parent != null && p.height == MATCH_PARENT ? parent.getMeasuredHeight() : p.height;
        return measure(view, w, h);
    }

    private static <T extends View> T measure(T view, int width, int height) {
        int w = MeasureSpec.makeMeasureSpec(width, width == WRAP_CONTENT ? UNSPECIFIED : EXACTLY);
        int h = MeasureSpec.makeMeasureSpec(height, height == WRAP_CONTENT ? UNSPECIFIED : EXACTLY);
        view.measure(w, h);
        return view;
    }

    /**
     * X position of the View's center, in pixels.
     *
     * @see View#getX()
     * @since 4.0.0
     */
    public static float getCenterX(View view) {
        return view.getX() + view.getWidth() / 2;
    }

    /**
     * Y position of the View's center, in pixels.
     *
     * @see View#getY()
     * @since 4.0.0
     */
    public static float getCenterY(View view) {
        return view.getY() + view.getHeight() / 2;
    }

    /**
     * Inflate the ViewStub if the View is null. Otherwise just get the previously inflated View.
     *
     * @since 4.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T inflate(ViewStub stub, @Nullable T view) {
        return view == null ? (T) stub.inflate() : view;
    }

    /**
     * Set the listener on all of the Views.
     *
     * @since 4.0.0
     */
    public static void setOnClickListeners(OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }
}
