/*
 * Copyright 2016-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.os;

import android.os.Bundle;

/**
 * Utility methods for working with Bundles.
 *
 * @since 4.0.0
 */
public class Bundles {
    private Bundles() {
    }

    /**
     * Get a Bundle of the key and value.
     */
    public static Bundle of(String key, int value) {
        Bundle b = new Bundle(1);
        b.putInt(key, value);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, int value1, String key2, int value2) {
        Bundle b = new Bundle(2);
        b.putInt(key1, value1);
        b.putInt(key2, value2);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, int value1, String key2, int value2, String key3,
                            int value3) {
        Bundle b = new Bundle(3);
        b.putInt(key1, value1);
        b.putInt(key2, value2);
        b.putInt(key3, value3);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, int value1, String key2, int value2, String key3,
                            int value3, String key4, int value4) {
        Bundle b = new Bundle(4);
        b.putInt(key1, value1);
        b.putInt(key2, value2);
        b.putInt(key3, value3);
        b.putInt(key4, value4);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, int value1, String key2, int value2, String key3,
                            int value3, String key4, int value4, String key5, int value5) {
        Bundle b = new Bundle(5);
        b.putInt(key1, value1);
        b.putInt(key2, value2);
        b.putInt(key3, value3);
        b.putInt(key4, value4);
        b.putInt(key5, value5);
        return b;
    }

    /**
     * Get a Bundle of the key and value.
     */
    public static Bundle of(String key, long value) {
        Bundle b = new Bundle(1);
        b.putLong(key, value);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, long value1, String key2, long value2) {
        Bundle b = new Bundle(2);
        b.putLong(key1, value1);
        b.putLong(key2, value2);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, long value1, String key2, long value2, String key3,
                            long value3) {
        Bundle b = new Bundle(3);
        b.putLong(key1, value1);
        b.putLong(key2, value2);
        b.putLong(key3, value3);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, long value1, String key2, long value2, String key3,
                            long value3, String key4, long value4) {
        Bundle b = new Bundle(4);
        b.putLong(key1, value1);
        b.putLong(key2, value2);
        b.putLong(key3, value3);
        b.putLong(key4, value4);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, long value1, String key2, long value2, String key3,
                            long value3, String key4, long value4, String key5, long value5) {
        Bundle b = new Bundle(5);
        b.putLong(key1, value1);
        b.putLong(key2, value2);
        b.putLong(key3, value3);
        b.putLong(key4, value4);
        b.putLong(key5, value5);
        return b;
    }

    /**
     * Get a Bundle of the key and value.
     */
    public static Bundle of(String key, float value) {
        Bundle b = new Bundle(1);
        b.putFloat(key, value);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, float value1, String key2, float value2) {
        Bundle b = new Bundle(2);
        b.putFloat(key1, value1);
        b.putFloat(key2, value2);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, float value1, String key2, float value2, String key3,
                            float value3) {
        Bundle b = new Bundle(3);
        b.putFloat(key1, value1);
        b.putFloat(key2, value2);
        b.putFloat(key3, value3);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, float value1, String key2, float value2, String key3,
                            float value3, String key4, float value4) {
        Bundle b = new Bundle(4);
        b.putFloat(key1, value1);
        b.putFloat(key2, value2);
        b.putFloat(key3, value3);
        b.putFloat(key4, value4);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, float value1, String key2, float value2, String key3,
                            float value3, String key4, float value4, String key5, float value5) {
        Bundle b = new Bundle(5);
        b.putFloat(key1, value1);
        b.putFloat(key2, value2);
        b.putFloat(key3, value3);
        b.putFloat(key4, value4);
        b.putFloat(key5, value5);
        return b;
    }

    /**
     * Get a Bundle of the key and value.
     */
    public static Bundle of(String key, String value) {
        Bundle b = new Bundle(1);
        b.putString(key, value);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, String value1, String key2, String value2) {
        Bundle b = new Bundle(2);
        b.putString(key1, value1);
        b.putString(key2, value2);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, String value1, String key2, String value2, String key3,
                            String value3) {
        Bundle b = new Bundle(3);
        b.putString(key1, value1);
        b.putString(key2, value2);
        b.putString(key3, value3);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, String value1, String key2, String value2, String key3,
                            String value3, String key4, String value4) {
        Bundle b = new Bundle(4);
        b.putString(key1, value1);
        b.putString(key2, value2);
        b.putString(key3, value3);
        b.putString(key4, value4);
        return b;
    }

    /**
     * Get a Bundle of the keys and values.
     */
    public static Bundle of(String key1, String value1, String key2, String value2, String key3,
                            String value3, String key4, String value4, String key5, String value5) {
        Bundle b = new Bundle(5);
        b.putString(key1, value1);
        b.putString(key2, value2);
        b.putString(key3, value3);
        b.putString(key4, value4);
        b.putString(key5, value5);
        return b;
    }
}
