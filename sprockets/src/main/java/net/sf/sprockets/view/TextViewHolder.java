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

package net.sf.sprockets.view;

import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.Bind;

/**
 * Holds TextViews that are commonly found in SDK layouts.
 *
 * @since 2.3.0
 */
public class TextViewHolder extends ViewHolder {
    @Bind(android.R.id.text1)
    public TextView text1;

    @Nullable
    @Bind(android.R.id.text2)
    public TextView text2;

    @Override
    protected TextViewHolder newInstance() {
        return new TextViewHolder();
    }
}
