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

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView adapter that inflates a layout when creating ViewHolders and provides the View to
 * its subclass.
 *
 * @since 4.0.0
 */
public abstract class ResourceAdapter<VH extends ViewHolder> extends Adapter<VH> {
    private final int mLayoutId;
    private final int[] mLayoutIds;

    /**
     * Inflate the layout when creating ViewHolders.
     */
    protected ResourceAdapter(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
        mLayoutIds = null;
    }

    /**
     * Inflate the layouts when creating ViewHolders. {@link #getItemViewType(int) getItemViewType}
     * return values must correspond to the array indices of the layout IDs.
     * i.e. {@code [0, layoutIds.length)}
     */
    protected ResourceAdapter(@LayoutRes int... layoutIds) {
        mLayoutId = 0;
        mLayoutIds = layoutIds;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType, LayoutInflater.from(parent.getContext())
                .inflate(mLayoutIds != null ? mLayoutIds[viewType] : mLayoutId, parent, false));
    }

    /**
     * See {@link Adapter#onCreateViewHolder(ViewGroup, int)}.
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType, View view);
}
