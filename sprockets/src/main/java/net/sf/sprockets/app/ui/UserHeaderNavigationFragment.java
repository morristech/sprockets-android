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

package net.sf.sprockets.app.ui;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

import net.sf.sprockets.R;
import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.SprocketsComponent;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.databinding.SprocketsNavigationHeaderBinding;

/**
 * Provides a NavigationView with a header displaying the currently logged in FirebaseUser. In (or
 * anytime after) {@code onViewCreated} you can get the NavigationView and inflate your menu, set
 * an OnNavigationItemSelectedListener, and perform any other customisations.
 * <p>
 * XML Attributes:
 * </p>
 * <ul>
 * <li>{@link #newInstance(int) sprockets_headerBackground}</li>
 * </ul>
 *
 * @since 4.0.0
 */
public class UserHeaderNavigationFragment extends SprocketsFragment {
    /**
     * Arguments key for a drawable resource ID.
     */
    private static final String HEADER_BACKGROUND =
            UserHeaderNavigationFragment.class.getName() + ".headerBackground";

    SprocketsNavigationHeaderBinding mBind;
    private final Auth mAuth = SprocketsComponent.get().auth();
    private final AuthStateListener mListener = auth -> mBind.setUser(auth.getCurrentUser());

    /**
     * Display the Drawable as the header background.
     */
    public static UserHeaderNavigationFragment newInstance(@DrawableRes int headerBackground) {
        UserHeaderNavigationFragment frag = new UserHeaderNavigationFragment();
        Bundle args = Fragments.arguments(frag);
        args.putInt(HEADER_BACKGROUND, headerBackground);
        return frag;
    }

    @Override
    public void onInflate(Activity a, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(a, attrs, savedInstanceState);
        TypedArray ar = a.obtainStyledAttributes(attrs, R.styleable.UserHeaderNavigationFragment);
        Bundle args = Fragments.arguments(this);
        args.putInt(HEADER_BACKGROUND, ar.getResourceId(
                R.styleable.UserHeaderNavigationFragment_sprockets_headerBackground, 0));
        ar.recycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sprockets_navigation, container, false);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBind = SprocketsNavigationHeaderBinding.bind(getNavigationView().getHeaderView(0));
        int background = Fragments.arguments(this).getInt(HEADER_BACKGROUND);
        if (background > 0) {
            mBind.background.setImageResource(background);
        }
        mAuth.addAuthStateListener(mListener);
    }

    /**
     * Will be null before {@link #onViewCreated(View, Bundle) onViewCreated}.
     */
    @Nullable
    public NavigationView getNavigationView() {
        return (NavigationView) getView();
    }

    /**
     * Will be null before {@link #onViewCreated(View, Bundle) onViewCreated}.
     */
    @Nullable
    public ImageView getHeaderBackground() {
        return mBind != null ? mBind.background : null;
    }

    /**
     * Will be null before {@link #onViewCreated(View, Bundle) onViewCreated}.
     */
    @Nullable
    public ImageView getUserPhoto() {
        return mBind != null ? mBind.photo : null;
    }

    /**
     * Will be null before {@link #onViewCreated(View, Bundle) onViewCreated}.
     */
    @Nullable
    public TextView getUserNameView() {
        return mBind != null ? mBind.name : null;
    }

    /**
     * Will be null before {@link #onViewCreated(View, Bundle) onViewCreated}.
     */
    @Nullable
    public TextView getUserEmailAddressView() {
        return mBind != null ? mBind.email : null;
    }

    @Override
    public void onDestroyView() {
        mAuth.removeAuthStateListener(mListener);
        super.onDestroyView();
    }
}
