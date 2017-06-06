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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.DefaultSpanSizeLookup;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Utility methods for working with RecyclerView LayoutManagers.
 *
 * @since 4.0.0
 */
public class LayoutManagers {
    private LayoutManagers() {
    }

    /**
     * Get the orientation of the RecyclerView's LayoutManager.
     *
     * @return -1 if the RecyclerView doesn't have a LayoutManager
     * @see RecyclerView#VERTICAL
     * @see RecyclerView#HORIZONTAL
     */
    public static int getOrientation(RecyclerView view) {
        LayoutManager layout = view.getLayoutManager();
        if (layout instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layout).getOrientation();
        } else if (layout instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layout).getOrientation();
        }
        return -1;
    }

    /**
     * Get the number of spans laid out by the RecyclerView's LayoutManager.
     *
     * @return 0 if the RecyclerView doesn't have a LayoutManager
     */
    public static int getSpanCount(RecyclerView view) {
        LayoutManager layout = view.getLayoutManager();
        if (layout != null) {
            if (layout instanceof GridLayoutManager) {
                return ((GridLayoutManager) layout).getSpanCount();
            } else if (layout instanceof StaggeredGridLayoutManager) {
                return ((StaggeredGridLayoutManager) layout).getSpanCount();
            }
            return 1; // assuming LinearLayoutManager
        }
        return 0;
    }

    private static DefaultSpanSizeLookup sDefSpanSizeLookup;

    /**
     * If the RecyclerView uses a {@link GridLayoutManager}, get its SpanSizeLookup. Otherwise get a
     * {@link DefaultSpanSizeLookup DefaultSpanSizeLookup}.
     */
    public static SpanSizeLookup getSpanSizeLookup(RecyclerView view) {
        LayoutManager layout = view.getLayoutManager();
        if (layout instanceof GridLayoutManager) {
            return ((GridLayoutManager) layout).getSpanSizeLookup();
        }
        if (sDefSpanSizeLookup == null) {
            sDefSpanSizeLookup = new DefaultSpanSizeLookup();
        }
        return sDefSpanSizeLookup;
    }
}
