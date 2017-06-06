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

package net.sf.sprockets.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

/**
 * <p>
 * Notifies observers when its Drawable has changed. Usage in XML is identical to {@link ImageView},
 * just with a different name:
 * </p>
 * <pre>{@code
 * <net.sf.sprockets.widget.ObservableImageView
 *     ... />
 * }</pre>
 *
 * @since 2.1.0
 */
public class ObservableImageView extends ImageView {
    private final Observable mObservable = new Observable();

    public ObservableImageView(Context context) {
        super(context);
    }

    public ObservableImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ObservableImageView(Context context, AttributeSet attrs, int defStyleAttr,
                               int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        notifyObservers();
    }

    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        notifyObservers();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        notifyObservers();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm); // calls setImageDrawable, which will call notifyObservers
    }

    @SuppressWarnings("ConstantConditions")
    private void notifyObservers() {
        if (mObservable != null) { // is during super(), which can be setting an image from XML
            mObservable.notifyObservers(getDrawable());
        }
    }

    /**
     * Notify the observer when the Drawable has changed.
     */
    public void addObserver(Observer observer) {
        mObservable.addObserver(observer);
    }

    /**
     * Stop notifying the observer when the Drawable has changed.
     */
    public void deleteObserver(Observer observer) {
        mObservable.deleteObserver(observer);
    }

    /**
     * Stop notifying all observers when the Drawable has changed.
     */
    public void deleteObservers() {
        mObservable.deleteObservers();
    }
}
