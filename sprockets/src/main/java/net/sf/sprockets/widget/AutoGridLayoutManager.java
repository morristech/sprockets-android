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

package net.sf.sprockets.widget;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;

/**
 * Automatically sets {@link #setSpanCount(int) span count} according to the available space and the
 * desired minimum size of each item.
 *
 * @since 4.0.0
 */
public class AutoGridLayoutManager extends GridLayoutManager {
    private final int mItemMinSize;

    public AutoGridLayoutManager(Context context, @DimenRes int itemMinWidthId) {
        this(context, itemMinWidthId, VERTICAL, false);
    }

    public AutoGridLayoutManager(Context context, @DimenRes int itemMinSizeId, int orientation,
                                 boolean reverseLayout) {
        super(context, DEFAULT_SPAN_COUNT, orientation, reverseLayout);
        mItemMinSize = context.getResources().getDimensionPixelSize(itemMinSizeId);
    }

    @Override
    public void onLayoutChildren(Recycler recycler, State state) {
        if (getSpanCount() == DEFAULT_SPAN_COUNT) {
            int parentSize = getOrientation() == VERTICAL ? getWidth() : getHeight();
            setSpanCount(Math.max(parentSize / mItemMinSize, 1));
        }
        super.onLayoutChildren(recycler, state);
    }
}
