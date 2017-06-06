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

package net.sf.sprockets.util;

import net.sf.sprockets.crash.Crash;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class FirebaseLogTest extends SprocketsTest {
    @Mock private Crash mCrash;
    @InjectMocks private FirebaseLog mLog;

    @Test
    public void testWithException() {
        Throwable thrown = new Throwable();
        mLog.e(this, "test error", thrown);
        verify(mCrash).log(anyString());
        verify(mCrash).report(thrown);
    }

    @Test
    public void testWithoutException() {
        mLog.w(this, "test warn");
        verify(mCrash).log(anyString());
        verify(mCrash).report(any(Throwable.class));
    }

    @Test
    public void testInfo() {
        mLog.i(this, "test info");
        verifyZeroInteractions(mCrash);
    }
}
