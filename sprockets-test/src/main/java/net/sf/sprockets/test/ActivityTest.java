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

package net.sf.sprockets.test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import net.sf.sprockets.lang.Classes;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

/**
 * Starts an Activity before each test.
 */
public abstract class ActivityTest<T extends Activity> extends SprocketsTest {
    @Rule @SuppressWarnings("unchecked") public final ActivityTestRule<T> activityTestRule =
            new ActivityTestRule<>((Class<T>) Classes.getTypeArgument(getClass(), "T"));

    /**
     * Activity which was started.
     */
    protected T a;

    @Before
    public void setActivityField() {
        a = activityTestRule.getActivity();
    }

    @After
    public void unsetActivityField() {
        a = null;
    }
}
