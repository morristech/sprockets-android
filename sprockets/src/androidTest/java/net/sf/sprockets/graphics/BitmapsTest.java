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

package net.sf.sprockets.graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static android.graphics.Bitmap.Config.ARGB_4444;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.graphics.Bitmap.Config.RGB_565;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class BitmapsTest extends SprocketsTest {
    @Test
    public void testByteCount() {
        assertEquals(60, Bitmaps.getByteCount(3, 5));
        assertEquals(15, Bitmaps.getByteCount(3, 5, ALPHA_8));
        assertEquals(30, Bitmaps.getByteCount(3, 5, RGB_565));
        assertEquals(30, Bitmaps.getByteCount(3, 5, ARGB_4444));
        assertEquals(60, Bitmaps.getByteCount(3, 5, ARGB_8888));
    }

    @Test
    public void testMutable() {
        Bitmap mutable = Bitmap.createBitmap(3, 5, ARGB_8888);
        assertSame(mutable, Bitmaps.mutable(mutable));
        Bitmap immutable =
                BitmapFactory.decodeResource(context.getResources(), android.R.drawable.star_on);
        assertFalse(immutable.isMutable());
        mutable = Bitmaps.mutable(immutable);
        assertNotNull(mutable);
        assertTrue(mutable.isMutable());
    }
}
