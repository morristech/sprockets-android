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
import android.support.annotation.ColorInt;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for working with Spans.
 *
 * @since 2.1.0
 */
public class Spans {
    private static StyleSpan sBold;
    private static StyleSpan sItalic;
    private static StyleSpan sBoldItalic;
    private static List<StyleSpan> sBolds;
    private static SparseArray<List<ForegroundColorSpan>> sForeColors;

    private Spans() {
    }

    /**
     * Get a cached bold span.
     *
     * @since 4.0.0
     */
    public static StyleSpan bold() {
        if (sBold == null) {
            sBold = new StyleSpan(Typeface.BOLD);
        }
        return sBold;
    }

    /**
     * Get a cached italic span.
     *
     * @since 4.0.0
     */
    public static StyleSpan italic() {
        if (sItalic == null) {
            sItalic = new StyleSpan(Typeface.ITALIC);
        }
        return sItalic;
    }

    /**
     * Get a cached bold italic span.
     *
     * @since 4.0.0
     */
    public static StyleSpan boldItalic() {
        if (sBoldItalic == null) {
            sBoldItalic = new StyleSpan(Typeface.BOLD_ITALIC);
        }
        return sBoldItalic;
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
            sBolds.add(bold());
        }
        if (i < sBolds.size()) {
            return sBolds.get(i);
        } else {
            StyleSpan bold = new StyleSpan(Typeface.BOLD);
            sBolds.add(bold);
            return bold;
        }
    }

    /**
     * Get a cached foreground color span.
     *
     * @param i starts at zero
     * @since 3.0.0
     */
    public static ForegroundColorSpan foregroundColor(@ColorInt int color, int i) {
        if (sForeColors == null) {
            sForeColors = new SparseArray<>();
        }
        List<ForegroundColorSpan> spans = sForeColors.get(color);
        if (spans == null) {
            spans = new ArrayList<>();
            sForeColors.put(color, spans);
        }
        if (i < spans.size()) {
            return spans.get(i);
        } else {
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            spans.add(span);
            return span;
        }
    }
}
