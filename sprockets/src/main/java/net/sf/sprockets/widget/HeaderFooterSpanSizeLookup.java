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

import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;

/**
 * Reports that the requested number of leading and trailing items should span all others.
 *
 * @since 4.0.0
 */
public class HeaderFooterSpanSizeLookup extends SpanSizeLookup {
    private final RecyclerView mList;
    private final int mHeaders;
    private final int mFooters;

    /**
     * @param headers number of leading items that should span all others
     * @param footers number of trailing items that should span all others
     */
    public HeaderFooterSpanSizeLookup(RecyclerView view, int headers, int footers) {
        mList = view;
        mHeaders = headers;
        mFooters = footers;
    }

    public int getHeaderCount() {
        return mHeaders;
    }

    public int getFooterCount() {
        return mFooters;
    }

    public int getHeaderFooterCount() {
        return mHeaders + mFooters;
    }

    public boolean isHeader(int position) {
        return position < mHeaders;
    }

    public boolean isFooter(int position) {
        Adapter adapter = mList.getAdapter();
        return adapter != null && position >= adapter.getItemCount() - mFooters;
    }

    public boolean isHeaderOrFooter(int position) {
        return isHeader(position) || isFooter(position);
    }

    @Override
    public int getSpanSize(int position) {
        return isHeaderOrFooter(position) ? LayoutManagers.getSpanCount(mList) : 1;
    }

    @Override
    public int getSpanIndex(int position, int spanCount) {
        return isHeaderOrFooter(position) ? 0 : (position - mHeaders) % spanCount;
    }
}
