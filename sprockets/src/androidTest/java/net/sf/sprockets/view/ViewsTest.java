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

package net.sf.sprockets.view;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewsTest extends SprocketsTest {
    private View mView;

    @Before
    public void resetBefore() {
        mView = new View(context);
        mView.setLayoutParams(new LayoutParams(3, 5));
    }

    @Test
    public void testVisibility() {
        assertTrue(Views.isVisible(mView));
        Views.invisible(mView);
        assertTrue(Views.isInvisible(mView));
        Views.gone(mView);
        assertTrue(Views.isGone(mView));
        Views.visible(mView);
        assertTrue(Views.isVisible(mView));
    }

    @Test
    public void testMeasure() {
        assertEquals(0, mView.getMeasuredWidth());
        assertEquals(0, mView.getMeasuredHeight());
        Views.measure(mView);
        assertEquals(3, mView.getMeasuredWidth());
        assertEquals(5, mView.getMeasuredHeight());
    }
}
