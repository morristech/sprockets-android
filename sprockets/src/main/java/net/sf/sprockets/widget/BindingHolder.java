/*
 * Copyright 2016-2017 pushbit <pushbit@gmail.com>
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

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Provides the layout binding. After updating the binding in
 * {@link Adapter#onBindViewHolder(ViewHolder, int) onBindViewHolder}, be sure to call
 * {@link ViewDataBinding#executePendingBindings() binding.executePendingBindings()} to avoid jank
 * when scrolling.
 *
 * @since 4.0.0
 */
public class BindingHolder<B extends ViewDataBinding> extends ViewHolder {
    public final B binding;

    /**
     * Inflate the layout and get its binding.
     */
    public BindingHolder(@LayoutRes int layout, ViewGroup parent) {
        this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    /**
     * Get the layout root's binding.
     */
    public BindingHolder(View root) {
        super(root);
        binding = DataBindingUtil.bind(root);
    }
}
