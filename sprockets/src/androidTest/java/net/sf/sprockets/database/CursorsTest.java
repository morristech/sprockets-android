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

package net.sf.sprockets.database;

import android.database.MatrixCursor;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CursorsTest extends SprocketsTest {
    private final MatrixCursor c = new MatrixCursor(new String[]{"one", "two"}, 3);

    public CursorsTest() {
        c.newRow().add(3).add(5);
        c.newRow().add(7).add(11);
        c.newRow().add(13).add(17);
    }

    @Test
    public void testIsActive() {
        c.moveToPosition(-1);
        assertFalse(Cursors.isActive(c));
        while (c.moveToNext()) {
            assertTrue(Cursors.isActive(c));
        }
        assertFalse(Cursors.isActive(c));
    }

    @Test
    public void testHasPosition() {
        c.moveToPosition(-1);
        assertFalse(Cursors.hasPosition(c, c.getPosition()));
        while (c.moveToNext()) {
            assertTrue(Cursors.hasPosition(c, c.getPosition()));
        }
        assertFalse(Cursors.hasPosition(c, c.getPosition()));
    }

    @Test
    public void testCount() {
        assertEquals(3, Cursors.count(c, false));
    }

    @Test
    public void testFirstInt() {
        assertEquals(3, Cursors.firstInt(c, false));
    }

    @Test
    public void testFirstLong() {
        assertEquals(3L, Cursors.firstLong(c, false));
    }

    @Test
    public void testFirstString() {
        assertEquals("3", Cursors.firstString(c, false));
    }

    @Test
    public void testAllInts() {
        assertArrayEquals(new int[]{3, 7, 13}, Cursors.allInts(c, false));
    }

    @Test
    public void testAllLongs() {
        assertArrayEquals(new long[]{3L, 7L, 13L}, Cursors.allLongs(c, false));
    }

    @Test
    public void testAllStrings() {
        assertArrayEquals(new String[]{"3", "7", "13"}, Cursors.allStrings(c, false));
    }
}
