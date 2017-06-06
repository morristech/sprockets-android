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

package net.sf.sprockets.text.style;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

/**
 * ForegroundColorSpan with alpha and color properties that can be changed after it's created. Note
 * that after updating these values, you will likely need to re-set the Spannable as the text for
 * the View in order to see the changes reflected.
 * <p>
 * Inspired by Flavien Laurent's <a href="http://flavienlaurent.com/blog/2014/01/31/spans/"
 * target="_blank">Spans, a Powerful Concept.</a>
 * </p>
 */
@SuppressLint("ParcelCreator")
public class MutableForegroundColorSpan extends ForegroundColorSpan {
    private int mAlpha;
    private int mColor;

    /**
     * Start with the alpha and color.
     *
     * @param alpha 0 (transparent) to 255 (opaque)
     * @param color e.g. 0x00FF00 for green
     */
    public MutableForegroundColorSpan(@IntRange(from = 0, to = 255) int alpha,
                                      @ColorInt int color) {
        super(color);
        mAlpha = alpha;
        mColor = color;
    }

    public MutableForegroundColorSpan(Parcel src) {
        super(src);
        mAlpha = src.readInt();
        mColor = src.readInt();
    }

    public MutableForegroundColorSpan setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mAlpha = alpha;
        return this;
    }

    public int getAlpha() {
        return mAlpha;
    }

    public MutableForegroundColorSpan setForegroundColor(@ColorInt int color) {
        mColor = color;
        return this;
    }

    /**
     * Get the result of applying the alpha to the color.
     */
    @Override
    public int getForegroundColor() {
        return Color.argb(mAlpha, Color.red(mColor), Color.green(mColor), Color.blue(mColor));
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(getForegroundColor());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mAlpha);
        dest.writeInt(mColor);
    }
}
