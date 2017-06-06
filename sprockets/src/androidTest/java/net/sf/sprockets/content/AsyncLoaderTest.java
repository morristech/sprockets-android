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

import android.content.Loader.OnLoadCompleteListener;
import android.os.SystemClock;
import android.support.test.annotation.UiThreadTest;

import net.sf.sprockets.test.MainThreadTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AsyncLoaderTest extends MainThreadTest {
    final String mData = "test";
    private AsyncLoader<String> mLoader;
    @Mock private OnLoadCompleteListener<String> mListener;

    @Before
    public void resetBefore() {
        mLoader = spy(new AsyncLoader<String>(targetContext) {
            @Override
            public String loadInBackground() {
                SystemClock.sleep(100L); // don't return too fast or might not be around to cancel
                return mData;
            }
        });
    }

    @Test
    @UiThreadTest
    public void testStart() {
        mLoader.startLoading();
        verify(mLoader).forceLoad();
    }

    @Test
    @UiThreadTest
    public void testLoadComplete() {
        mLoader.registerListener(0, mListener);
        mLoader.startLoading();
        mLoader.deliverResult(mLoader.loadInBackground());
        verify(mListener).onLoadComplete(mLoader, mData);
    }

    @Test
    @UiThreadTest
    public void testStop() {
        mLoader.startLoading();
        mLoader.stopLoading();
        verify(mLoader).cancelLoadInBackground();
    }

    @Test
    @UiThreadTest
    @SuppressWarnings("unchecked")
    public void testContentChanged() {
        testLoadComplete();
        mLoader.stopLoading();
        clearInvocations(mLoader);
        mLoader.onContentChanged();
        testStart();
    }

    @Test
    @UiThreadTest
    @SuppressWarnings("unchecked")
    public void testReset() {
        testLoadComplete();
        mLoader.reset();
        clearInvocations(mLoader);
        testStart();
    }
}
