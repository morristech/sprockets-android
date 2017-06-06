/*
 * Copyright 2016 pushbit <pushbit@gmail.com>
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

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView adapter that provides a layout's binding to its subclass when binding items to
 * Views.
 *
 * @since 4.0.0
 */
public abstract class BindingAdapter<B extends ViewDataBinding>
        extends ResourceAdapter<BindingHolder<B>> {
    /**
     * Provide the layout's binding when binding data to Views.
     */
    protected BindingAdapter(@LayoutRes int layoutId) {
        super(layoutId);
    }

    @Override
    public BindingHolder<B> onCreateViewHolder(ViewGroup parent, int viewType, View view) {
        return new BindingHolder<>(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder<B> holder, int position) {
        onBindViewHolder(holder, position, null);
    }

    @Override
    public void onBindViewHolder(BindingHolder<B> holder, int position, List<Object> payloads) {
        if (payloads != null) {
            onBind(holder.binding, position, payloads);
        } else {
            onBind(holder.binding, position);
        }
        holder.binding.executePendingBindings();
    }

    /**
     * Update the contents of the binding to reflect the item at the position.
     */
    public abstract void onBind(B binding, int position);

    /**
     * Update the contents of the binding to reflect the item at the position. Any payload info can
     * be used to run an efficient partial update.
     */
    public void onBind(B binding, int position, List<Object> payloads) {
        onBind(binding, position);
    }
}
