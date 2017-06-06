/*
 * Copyright 2017 pushbit <pushbit@gmail.com>
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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.ViewInteraction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;

import net.sf.sprockets.R;
import net.sf.sprockets.app.ui.NavigationDrawerActivityTest.TestActivity;
import net.sf.sprockets.test.ActivityTest;

import org.junit.Test;

import static android.graphics.Color.TRANSPARENT;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.v4.view.GravityCompat.START;
import static android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NavigationDrawerActivityTest extends ActivityTest<TestActivity> {
    @Test
    public void testStatusBarColor() {
        assertEquals(TRANSPARENT, a.getWindow().getStatusBarColor());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testActionBar() {
        assertEquals(DISPLAY_HOME_AS_UP,
                a.getSupportActionBar().getDisplayOptions() & DISPLAY_HOME_AS_UP);
    }

    @Test
    public void testNavigationDrawerButton() {
        navButton().perform(click());
        assertTrue(a.mDrawer.isDrawerVisible(START));
        navButton().perform(click());
        assertFalse(a.mDrawer.isDrawerVisible(START));
    }

    @Test
    public void testCloseNavigationDrawerWithBackButton() {
        navButton().perform(click());
        assertTrue(a.mDrawer.isDrawerVisible(START));
        pressBack();
        assertFalse(a.mDrawer.isDrawerVisible(START));
    }

    private ViewInteraction navButton() {
        return onView(withContentDescription(R.string.content_navigation_menu));
    }

    public static class TestActivity extends NavigationDrawerActivity {
        DrawerLayout mDrawer;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mDrawer = new DrawerLayout(this);
            mDrawer.setFitsSystemWindows(true);
            mDrawer.addView(new View(this)); // main content
            mDrawer.addView(new View(this), // navigation drawer
                    new DrawerLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, START));
            addContentView(mDrawer, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            setDrawerLayout(mDrawer);
        }
    }
}
