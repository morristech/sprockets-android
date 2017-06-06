/*
 * Copyright 2014-2016 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.graphics.drawable;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.TextView;

import net.sf.sprockets.content.res.Themes;
import net.sf.sprockets.graphics.Colors;

/**
 * Utility methods for working with Drawables.
 */
public class Drawables {
    private Drawables() {
    }

    /**
     * Tint the Drawable with the color specified by the attribute in the Context's theme.
     *
     * @return null if the drawable does not exist
     * @since 4.0.0
     */
    @Nullable
    public static Drawable tint(Context context, @DrawableRes int drawableId, @AttrRes int attrId) {
        Drawable d = context.getDrawable(drawableId);
        if (d != null) {
            d.setTint(Themes.getColor(context, attrId));
        }
        return d;
    }

    /**
     * Set the Drawable's size and tint to be the same as the text.
     *
     * @return null if the drawable does not exist
     * @since 4.0.0
     */
    @Nullable
    public static Drawable matchText(Context context, @DrawableRes int drawableId, TextView text) {
        Drawable d = context.getDrawable(drawableId);
        if (d != null) {
            int size = Math.round(text.getTextSize());
            d.setBounds(0, 0, size, size);
            d.setTint(text.getCurrentTextColor());
        }
        return d;
    }

    /**
     * Get a random color.
     */
    public static ColorDrawable randomColor() {
        return color(Colors.random());
    }

    /**
     * Get a random color from the darkest sixth.
     */
    public static ColorDrawable darkestColor() {
        return color(Colors.darkest());
    }

    /**
     * Get a random color from the middle darker.
     */
    public static ColorDrawable darkerColor() {
        return color(Colors.darker());
    }

    /**
     * Get a random color from the lightest dark.
     */
    public static ColorDrawable darkColor() {
        return color(Colors.dark());
    }

    /**
     * Get a random color from the darkest light.
     */
    public static ColorDrawable lightColor() {
        return color(Colors.light());
    }

    /**
     * Get a random color from the middle lighter.
     */
    public static ColorDrawable lighterColor() {
        return color(Colors.lighter());
    }

    /**
     * Get a random color from the lightest sixth.
     */
    public static ColorDrawable lightestColor() {
        return color(Colors.lightest());
    }

    private static ColorDrawable color(int color) {
        return new ColorDrawable(color);
    }

    /**
     * Get a random color oval.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable randomOval() {
        return oval(Colors.random());
    }

    /**
     * Get a random color oval from the darkest sixth.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable darkestOval() {
        return oval(Colors.darkest());
    }

    /**
     * Get a random color oval from the middle darker.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable darkerOval() {
        return oval(Colors.darker());
    }

    /**
     * Get a random color oval from the lightest dark.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable darkOval() {
        return oval(Colors.dark());
    }

    /**
     * Get a random color oval from the darkest light.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable lightOval() {
        return oval(Colors.light());
    }

    /**
     * Get a random color oval from the middle lighter.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable lighterOval() {
        return oval(Colors.lighter());
    }

    /**
     * Get a random color oval from the lightest sixth.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable lightestOval() {
        return oval(Colors.lightest());
    }

    private static OvalShape sOval;

    /**
     * Get a colored oval.
     *
     * @since 2.2.0
     */
    public static ShapeDrawable oval(@ColorInt int color) {
        if (sOval == null) {
            sOval = new OvalShape();
        }
        ShapeDrawable d = new ShapeDrawable(sOval);
        d.setIntrinsicWidth(-1);
        d.setIntrinsicHeight(-1);
        d.getPaint().setColor(color);
        return d;
    }
}
