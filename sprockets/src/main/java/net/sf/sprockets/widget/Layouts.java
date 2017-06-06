/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.widget;

import android.view.View;
import android.widget.RelativeLayout;

/**
 * Utility methods for working with Layouts.
 *
 * @since 4.0.0
 */
public class Layouts {
    private Layouts() {
    }

    /**
     * Get the View's LayoutParams, cast to your assignment type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParams(View view) {
        return (T) view.getLayoutParams();
    }

    /**
     * Add the rule to the View's RelativeLayout params and request a layout of the View.
     */
    public static RelativeLayout.LayoutParams addRule(View view, int verb, int anchor) {
        RelativeLayout.LayoutParams params = getParams(view);
        params.addRule(verb, anchor);
        view.requestLayout();
        return params;
    }
}
