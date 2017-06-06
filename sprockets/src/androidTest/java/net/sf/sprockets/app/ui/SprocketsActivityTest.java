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

import net.sf.sprockets.app.ui.SprocketsActivityTest.TestActivity;
import net.sf.sprockets.test.ActivityTest;

import org.junit.Test;

import static android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SprocketsActivityTest extends ActivityTest<TestActivity> {
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testActionBar() {
        assertEquals(DISPLAY_HOME_AS_UP,
                a.getSupportActionBar().getDisplayOptions() & DISPLAY_HOME_AS_UP);
    }

    @Test
    public void testPresenterStarted() {
        verify(a.mPres).onStart(false);
    }

    public static class TestActivity extends SprocketsActivity {
        final Presenter mPres = mock(Presenter.class);

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            manage(mPres);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            verify(mPres).onStop();
        }
    }
}
