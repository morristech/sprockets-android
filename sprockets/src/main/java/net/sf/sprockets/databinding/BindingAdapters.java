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

package net.sf.sprockets.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.sf.sprockets.picasso.Transformations;

/**
 * Adds support for data binding expressions in {@link LayoutParams} and {@link MarginLayoutParams}
 * attributes. Also provides custom attributes for various Views.
 *
 * @since 4.0.0
 */
public class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter("android:layout_width")
    public static void layoutWidth(View view, float width) {
        LayoutParams params = view.getLayoutParams();
        params.width = Math.round(width);
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_height")
    public static void layoutHeight(View view, float height) {
        LayoutParams params = view.getLayoutParams();
        params.height = Math.round(height);
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_marginStart")
    public static void layoutMarginStart(View view, float start) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.setMarginStart(Math.round(start));
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_marginTop")
    public static void layoutMarginTop(View view, float top) {
        MarginLayoutParams p = (MarginLayoutParams) view.getLayoutParams();
        p.setMargins(p.leftMargin, Math.round(top), p.rightMargin, p.bottomMargin);
        view.setLayoutParams(p);
    }

    @BindingAdapter("android:layout_marginEnd")
    public static void layoutMarginEnd(View view, float end) {
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.setMarginEnd(Math.round(end));
        view.setLayoutParams(params);
    }

    @BindingAdapter("android:layout_marginBottom")
    public static void layoutMarginBottom(View view, float bottom) {
        MarginLayoutParams p = (MarginLayoutParams) view.getLayoutParams();
        p.setMargins(p.leftMargin, p.topMargin, p.rightMargin, Math.round(bottom));
        view.setLayoutParams(p);
    }

    @BindingAdapter("drawableStartAlpha")
    public static void drawableStartAlpha(TextView view, int alpha) {
        drawableAlpha(view, 0, alpha);
    }

    @BindingAdapter("drawableTopAlpha")
    public static void drawableTopAlpha(TextView view, int alpha) {
        drawableAlpha(view, 1, alpha);
    }

    @BindingAdapter("drawableEndAlpha")
    public static void drawableEndAlpha(TextView view, int alpha) {
        drawableAlpha(view, 2, alpha);
    }

    @BindingAdapter("drawableBottomAlpha")
    public static void drawableBottomAlpha(TextView view, int alpha) {
        drawableAlpha(view, 3, alpha);
    }

    private static void drawableAlpha(TextView view, int i, int alpha) {
        Drawable d = view.getCompoundDrawablesRelative()[i];
        if (d != null) {
            d.setAlpha(alpha);
        }
    }

    /**
     * Set a placeholder before loading an image, optionally resizing and transforming it. All
     * attributes are optional.
     *
     * @param transform can be "circle"
     */
    @BindingAdapter(value = {"sprockets_placeholder", "sprockets_load", "sprockets_resize",
            "sprockets_transform"}, requireAll = false)
    public static void load(ImageView view, Drawable placeholder, Uri load, boolean resize,
                            String transform) {
        RequestCreator req = Picasso.with(view.getContext()).load(load).placeholder(placeholder);
        if (resize) {
            req.fit().centerCrop(); // view width/height isn't available yet
        }
        if (TextUtils.equals(transform, "circle")) {
            req.transform(Transformations.circle());
        }
        req.into(view);
    }
}
