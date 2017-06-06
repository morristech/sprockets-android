/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

import android.content.ContentValues;
import android.support.v4.util.Pools.SynchronizedPool;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Utility methods for working with ContentValues.
 *
 * @since 4.0.0
 */
public class Values {
    private static final SynchronizedPool<ContentValues> sPool = new SynchronizedPool<>(8);

    private Values() {
    }

    /**
     * Get an empty set of values from the pool. Make sure to
     * {@link #release(ContentValues) release} it back to the pool when you no longer need it.
     */
    public static ContentValues acquire() {
        ContentValues vals = sPool.acquire();
        return vals != null ? vals : new ContentValues();
    }

    /**
     * Get a populated set of values from the pool. Make sure to
     * {@link #release(ContentValues) release} it back to the pool when you no longer need it.
     *
     * @param values length must be a multiple of two: {@code String key, Object value, ...}
     * @throws IllegalArgumentException if a value type is not supported
     */
    public static ContentValues acquire(Object... values) {
        return putAll(acquire(), values);
    }

    /**
     * Return the set of values to the pool. Do not use the set again after releasing it.
     *
     * @return true if the set was put in the pool
     */
    public static boolean release(ContentValues values) {
        values.clear();
        return sPool.release(values);
    }

    /**
     * Put all of the values into the existing set.
     *
     * @param values length must be a multiple of two: {@code String key, Object value, ...}
     * @throws IllegalArgumentException if a value type is not supported
     */
    public static ContentValues putAll(ContentValues target, Object... values) {
        int length = values.length;
        checkArgument(length % 2 == 0, "values length must be a multiple of two");
        for (int i = 0; i < length; i += 2) {
            String key = (String) values[i];
            Object val = values[i + 1];
            if (val == null) {
                target.putNull(key);
            } else if (val instanceof String) {
                target.put(key, (String) val);
            } else if (val instanceof Long) {
                target.put(key, (Long) val);
            } else if (val instanceof Integer) {
                target.put(key, (Integer) val);
            } else if (val instanceof Boolean) {
                target.put(key, (Boolean) val);
            } else if (val instanceof Double) {
                target.put(key, (Double) val);
            } else if (val instanceof Float) {
                target.put(key, (Float) val);
            } else if (val instanceof byte[]) {
                target.put(key, (byte[]) val);
            } else if (val instanceof Byte) {
                target.put(key, (Byte) val);
            } else if (val instanceof Short) {
                target.put(key, (Short) val);
            } else {
                throw new IllegalArgumentException(
                        "ContentValues does not support values of type " + val.getClass().getName()
                                + " (provided for key '" + key + "')");
            }
        }
        return target;
    }
}
