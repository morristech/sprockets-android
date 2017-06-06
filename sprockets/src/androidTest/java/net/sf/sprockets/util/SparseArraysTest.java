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

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SparseArraysTest extends SprocketsTest {
    @Test
    public void testKeysAndValues() {
        SparseArray<String> array = new SparseArray<>(3);
        array.append(3, "three");
        array.append(5, "five");
        array.append(7, "seven");
        assertArrayEquals(new int[]{3, 5, 7}, SparseArrays.keys(array));
        assertEquals(Arrays.asList("three", "five", "seven"), SparseArrays.values(array));
    }

    @Test
    public void testTrueAndFalseKeys() {
        SparseBooleanArray array = new SparseBooleanArray(3);
        array.append(3, true);
        array.append(5, false);
        array.append(7, true);
        assertArrayEquals(new int[]{3, 7}, SparseArrays.trueKeys(array));
        assertArrayEquals(new int[]{5}, SparseArrays.falseKeys(array));
        assertEquals(3, SparseArrays.firstTrueKey(array));
        assertEquals(5, SparseArrays.firstFalseKey(array));
    }
}
