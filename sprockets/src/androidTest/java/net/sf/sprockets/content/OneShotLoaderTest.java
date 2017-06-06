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

package net.sf.sprockets.content;

import android.support.test.annotation.UiThreadTest;

import net.sf.sprockets.test.MainThreadTest;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OneShotLoaderTest extends MainThreadTest {
    final String mData = "test";
    private OneShotLoader<String> mLoader;

    @Before
    public void resetBefore() {
        mLoader = spy(new OneShotLoader<String>(targetContext) {
            @Override
            public String loadInBackground() {
                return mData;
            }
        });
    }

    @Test
    public void testStart() {
        mLoader.startLoading();
        verify(mLoader).forceLoad();
    }

    @Test
    public void testAlreadyLoading() {
        mLoader.startLoading();
        mLoader.startLoading();
        verify(mLoader).forceLoad();
    }

    @Test
    @UiThreadTest
    public void testAlreadyLoaded() {
        mLoader.deliverResult(mLoader.loadInBackground());
        mLoader.startLoading();
        verify(mLoader, times(2)).deliverResult(mData);
    }
}
