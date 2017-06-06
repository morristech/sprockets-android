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

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.annotation.UiThreadTest;

import net.sf.sprockets.app.ui.SprocketsFragmentTest.TestActivity;
import net.sf.sprockets.test.ActivityTest;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SprocketsFragmentTest extends ActivityTest<TestActivity> {
    @Test
    @UiThreadTest
    public void testActivityAvailability() {
        assertNotNull(a.mFrag.a());
        removeFragment();
        assertNull(a.mFrag.a());
    }

    @Test
    @UiThreadTest
    public void testPresenterStartedAndStopped() {
        verify(a.mFrag.mPres).onStart(false);
        removeFragment();
        verify(a.mFrag.mPres).onStop();
    }

    private void removeFragment() {
        FragmentManager fm = a.getFragmentManager();
        fm.beginTransaction().remove(a.mFrag).commit();
        fm.executePendingTransactions();
    }

    public static class TestActivity extends SprocketsActivity {
        final TestFragment mFrag = new TestFragment();

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getFragmentManager().beginTransaction().add(mFrag, null).commit();
        }
    }

    public static class TestFragment extends SprocketsFragment {
        final Presenter mPres = mock(Presenter.class);

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            manage(mPres);
        }
    }
}
