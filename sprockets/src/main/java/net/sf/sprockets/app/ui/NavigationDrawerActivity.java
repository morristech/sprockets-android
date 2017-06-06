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

package net.sf.sprockets.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import net.sf.sprockets.R;

import static android.graphics.Color.TRANSPARENT;
import static android.support.v4.view.GravityCompat.START;

/**
 * Connects a DrawerLayout to the ActionBar so that the navigation button opens and closes the
 * drawer. Also closes the drawer when the back button is pressed.
 */
public abstract class NavigationDrawerActivity extends SprocketsActivity {
    private DrawerLayout mDrawer;

    /**
     * Connect the DrawerLayout to the ActionBar. Should be called in
     * {@link #onCreate(Bundle) onCreate}. Must be called before
     * {@link Activity#onPostCreate(Bundle) onPostCreate} executes.
     */
    public NavigationDrawerActivity setDrawerLayout(DrawerLayout drawer) {
        mDrawer = drawer;
        if (mDrawer.getFitsSystemWindows()) { // let it draw the status bar
            getWindow().setStatusBarColor(TRANSPARENT);
        }
        return this;
    }

    /**
     * Previously set DrawerLayout.
     */
    @Nullable
    public DrawerLayout getDrawerLayout() {
        return mDrawer;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawer != null) {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setHomeAsUpIndicator(R.drawable.sprockets_ic_menu);
                ab.setHomeActionContentDescription(R.string.content_navigation_menu);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawer != null) {
                    if (mDrawer.isDrawerVisible(START)) {
                        mDrawer.closeDrawer(START);
                    } else {
                        mDrawer.openDrawer(START);
                    }
                    return true;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerVisible(START)) {
            mDrawer.closeDrawer(START);
        } else {
            super.onBackPressed();
        }
    }
}
