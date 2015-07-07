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

package net.sf.sprockets.text.style;

import android.graphics.Typeface;
import android.text.style.StyleSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of Spans.
 *
 * @since 2.1.0
 */
public class Spans {
    public static final StyleSpan BOLD = new StyleSpan(Typeface.BOLD);
    public static final StyleSpan ITALIC = new StyleSpan(Typeface.ITALIC);
    public static final StyleSpan BOLD_ITALIC = new StyleSpan(Typeface.BOLD_ITALIC);
    private static List<StyleSpan> sBolds;

    private Spans() {
    }

    /**
     * Get a cached bold span.
     *
     * @param i starts at zero
     * @since 2.6.0
     */
    public static StyleSpan bold(int i) {
        if (sBolds == null) {
            sBolds = new ArrayList<>();
            sBolds.add(BOLD);
        }
        if (i < sBolds.size()) {
            return sBolds.get(i);
        } else {
            StyleSpan bold = new StyleSpan(Typeface.BOLD);
            sBolds.add(bold);
            return bold;
        }
    }
}
