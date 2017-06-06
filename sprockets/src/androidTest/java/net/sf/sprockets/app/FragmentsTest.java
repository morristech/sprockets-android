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

package net.sf.sprockets.app;

import android.app.Fragment;
import android.os.Bundle;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;

public class FragmentsTest extends SprocketsTest {
    @Mock private Fragment mFrag;

    @Test
    public void testExistingArguments() {
        // TODO_ use mock when can mock final methods on Android
        Fragment frag = new Fragment();
        Bundle args = new Bundle();
        frag.setArguments(args);
        assertSame(args, Fragments.arguments(frag));
    }

    @Test
    public void testNewArguments() {
        Bundle args = Fragments.arguments(mFrag);
        assertNotNull(args);
        verify(mFrag).setArguments(args);
    }

    @Test
    public void testEmptyArguments() {
        // TODO_ uncomment when can mock final methods on Android
        //when(mFrag.isAdded()).thenReturn(true);
        //assertSame(EMPTY, Fragments.arguments(mFrag));
    }
}
