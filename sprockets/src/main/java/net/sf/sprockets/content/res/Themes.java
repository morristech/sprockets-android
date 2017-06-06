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

package net.sf.sprockets.content.res;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Utility methods for working with theme attributes.
 */
public class Themes {
    private static final String TAG = Themes.class.getSimpleName();

    private Themes() {
    }

    /**
     * Get the color specified by the attribute in the Context's theme.
     *
     * @since 4.0.0
     */
    public static int getColor(Context context, @AttrRes int attrId) {
        int color = 0;
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        try {
            color = a.getColor(0, 0);
        } catch (UnsupportedOperationException e) {
            error("color", attrId, e);
        }
        a.recycle();
        return color;
    }

    /**
     * Get the Drawable specified by the attribute in the Context's theme.
     *
     * @return null if the drawable does not exist
     * @since 4.0.0
     */
    @Nullable
    public static Drawable getDrawable(Context context, @AttrRes int attrId) {
        Drawable d = null;
        TypedArray a = context.obtainStyledAttributes(new int[]{attrId});
        try {
            d = a.getDrawable(0);
        } catch (UnsupportedOperationException e) {
            error("drawable", attrId, e);
        }
        a.recycle();
        return d;
    }

    private static void error(String type, int attrId, UnsupportedOperationException e) {
        Log.e(TAG, "attribute " + Integer.toHexString(attrId) + " is not a " + type, e);
    }

    private static final int[] sActionBarSize = {android.R.attr.actionBarSize};

    /**
     * Get the ActionBar height in the Context's theme.
     */
    public static int getActionBarSize(Context context) {
        TypedArray a = context.obtainStyledAttributes(sActionBarSize);
        int size = a.getDimensionPixelSize(0, 0);
        a.recycle();
        return size;
    }

    /**
     * Get the ActionBar background in the Context's theme.
     *
     * @return null if a background is not defined
     */
    @Nullable
    public static Drawable getActionBarBackground(Context context) {
        int[] attrs = {android.R.attr.actionBarStyle};
        TypedArray a = context.obtainStyledAttributes(attrs);
        int id = a.getResourceId(0, 0);
        a.recycle();
        if (id > 0) {
            attrs[0] = android.R.attr.background;
            a = context.obtainStyledAttributes(id, attrs);
            Drawable background = a.getDrawable(0);
            a.recycle();
            return background;
        }
        return null;
    }
}
