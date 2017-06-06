/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
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

import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.apache.commons.collections.primitives.ArrayIntList;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for working with SparseArrays.
 */
public class SparseArrays {
    private SparseArrays() {
    }

    /**
     * Get the keys of the SparseArray.
     */
    public static int[] keys(SparseArray<?> array) {
        return (int[]) keys(array, null, null, null, null);
    }

    /**
     * Get the values of the SparseArray.
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> values(SparseArray<E> array) {
        return (List<E>) values(array, null, null, null, null);
    }

    /**
     * Get the keys of the SparseBooleanArray.
     */
    public static int[] keys(SparseBooleanArray array) {
        return (int[]) keys(null, array, null, null, null);
    }

    /**
     * Get the values of the SparseBooleanArray.
     */
    public static boolean[] values(SparseBooleanArray array) {
        return (boolean[]) values(null, array, null, null, null);
    }

    /**
     * Get the keys of the SparseBooleanArray whose value is true.
     */
    public static int[] trueKeys(SparseBooleanArray array) {
        return keys(array, true);
    }

    /**
     * Get the keys of the SparseBooleanArray whose value is false.
     */
    public static int[] falseKeys(SparseBooleanArray array) {
        return keys(array, false);
    }

    private static int[] keys(SparseBooleanArray array, boolean value) {
        int size = array.size();
        ArrayIntList keys = new ArrayIntList(size);
        for (int i = 0; i < size; i++) {
            if (array.valueAt(i) == value) {
                keys.add(array.keyAt(i));
            }
        }
        return keys.toArray();
    }

    /**
     * Get the first key of the SparseBooleanArray whose value is true.
     *
     * @return {@link Integer#MIN_VALUE} if no values are true
     */
    public static int firstTrueKey(SparseBooleanArray array) {
        return firstKey(array, true);
    }

    /**
     * Get the first key of the SparseBooleanArray whose value is false.
     *
     * @return {@link Integer#MIN_VALUE} if no values are false
     */
    public static int firstFalseKey(SparseBooleanArray array) {
        return firstKey(array, false);
    }

    private static int firstKey(SparseBooleanArray array, boolean value) {
        for (int i = 0, size = array.size(); i < size; i++) {
            if (array.valueAt(i) == value) {
                return array.keyAt(i);
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Get the keys of the SparseIntArray.
     */
    public static int[] keys(SparseIntArray array) {
        return (int[]) keys(null, null, array, null, null);
    }

    /**
     * Get the values of the SparseIntArray.
     */
    public static int[] values(SparseIntArray array) {
        return (int[]) values(null, null, array, null, null);
    }

    /**
     * Get the keys of the SparseLongArray.
     */
    public static int[] keys(SparseLongArray array) {
        return (int[]) keys(null, null, null, array, null);
    }

    /**
     * Get the values of the SparseLongArray.
     */
    public static long[] values(SparseLongArray array) {
        return (long[]) values(null, null, null, array, null);
    }

    /**
     * Get the keys of the LongSparseArray.
     */
    public static long[] keys(LongSparseArray<?> array) {
        return (long[]) keys(null, null, null, null, array);
    }

    /**
     * Get the values of the LongSparseArray.
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> values(LongSparseArray<E> array) {
        return (List<E>) values(null, null, null, null, array);
    }

    private static Object keys(SparseArray<?> a, SparseBooleanArray b, SparseIntArray c,
                               SparseLongArray d, LongSparseArray<?> e) {
        int size = size(a, b, c, d, e);
        int[] ints = a != null || b != null || c != null || d != null ? new int[size] : null;
        long[] longs = e != null ? new long[size] : null;
        for (int i = 0; i < size; i++) {
            if (ints != null) {
                ints[i] = a != null ? a.keyAt(i)
                        : b != null ? b.keyAt(i) : c != null ? c.keyAt(i) : d.keyAt(i);
            } else if (longs != null) {
                longs[i] = e.keyAt(i);
            }
        }
        return ints != null ? ints : longs;
    }

    private static <E> Object values(SparseArray<E> a, SparseBooleanArray b, SparseIntArray c,
                                     SparseLongArray d, LongSparseArray<E> e) {
        int size = size(a, b, c, d, e);
        ArrayList<E> vals = a != null || e != null ? new ArrayList<>(size) : null;
        boolean[] bools = b != null ? new boolean[size] : null;
        int[] ints = c != null ? new int[size] : null;
        long[] longs = d != null ? new long[size] : null;
        for (int i = 0; i < size; i++) {
            if (vals != null) {
                vals.add(a != null ? a.valueAt(i) : e.valueAt(i));
            } else if (bools != null) {
                bools[i] = b.valueAt(i);
            } else if (ints != null) {
                ints[i] = c.valueAt(i);
            } else if (longs != null) {
                longs[i] = d.valueAt(i);
            }
        }
        return vals != null ? vals : bools != null ? bools : ints != null ? ints : longs;
    }

    private static int size(SparseArray<?> a, SparseBooleanArray b, SparseIntArray c,
                            SparseLongArray d, LongSparseArray<?> e) {
        return a != null ? a.size()
                : b != null ? b.size() : c != null ? c.size() : d != null ? d.size() : e.size();
    }
}
