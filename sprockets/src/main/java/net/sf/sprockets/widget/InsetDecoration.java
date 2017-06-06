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

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import net.sf.sprockets.R;

import static android.support.v7.widget.RecyclerView.HORIZONTAL;
import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Insets items by a specified amount.
 *
 * @since 4.0.0
 */
public class InsetDecoration extends ItemDecoration {
    private final int mThird;
    private final int mHalf;
    private final int mTwoThirds;

    /**
     * Inset items by {@code R.dimen.list_item_margin}.
     */
    public InsetDecoration(Context context) {
        this(context, R.dimen.list_item_margin);
    }

    /**
     * Inset items by the dimension resource.
     */
    public InsetDecoration(Context context, @DimenRes int insetId) {
        float inset = context.getResources().getDimension(insetId);
        mThird = Math.max((int) (inset / 3), 1);
        mHalf = Math.max((int) (inset / 2), 1);
        mTwoThirds = Math.max((int) (inset * 2 / 3), 1);
    }

    @Override
    public void getItemOffsets(Rect rect, View view, RecyclerView parent, State state) {
        int pos = parent.getChildLayoutPosition(view);
        if (pos == NO_POSITION) {
            return;
        }
        int items = state.getItemCount();
        int spans = LayoutManagers.getSpanCount(parent);
        SpanSizeLookup lookup = LayoutManagers.getSpanSizeLookup(parent);
        /* vertical orientation terminology, mentally swap "row" and "col[umn]" for horizontal */
        int row = lookup.getSpanGroupIndex(pos, spans);
        int col = lookup.getSpanIndex(pos, spans);
        int nextCol = col + lookup.getSpanSize(pos);
        boolean row1 = row == 0;
        boolean rowN = pos == items - 1 || row == lookup.getSpanGroupIndex(items - 1, spans);
        boolean col1 = col == 0;
        boolean col2 = col == 1;
        boolean col2N = nextCol == spans - 1;
        boolean colN = nextCol == spans;
        rect.top = row1 ? 0 : mHalf;
        rect.bottom = rowN ? 0 : mHalf;
        if (spans <= 2) {
            rect.left = col1 ? 0 : mHalf;
            rect.right = colN ? 0 : mHalf;
        } else { // inset first and last columns more to keep all items same size
            rect.left = col1 ? 0 : col2 ? mThird : colN ? mTwoThirds : mHalf;
            rect.right = col1 && !colN ? mTwoThirds : col2N ? mThird : colN ? 0 : mHalf;
        }
        if (LayoutManagers.getOrientation(parent) == HORIZONTAL) { // rotate
            //noinspection SuspiciousNameCombination
            rect.set(rect.top, rect.left, rect.bottom, rect.right);
        }
    }
}
