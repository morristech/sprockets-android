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

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * SharedPreferences with additional methods which can simplify interaction.
 *
 * @since 4.0.0
 */
public interface EasyPreferences extends SharedPreferences {
    void putBoolean(String key, boolean value);

    /**
     * Returns false if the key does not exist.
     */
    boolean getBoolean(String key);

    void putInt(String key, int value);

    /**
     * Returns 0 if the key does not exist.
     */
    int getInt(String key);

    void putLong(String key, long value);

    /**
     * Returns 0 if the key does not exist.
     */
    long getLong(String key);

    void putFloat(String key, float value);

    /**
     * Returns 0 if the key does not exist.
     */
    float getFloat(String key);

    void putDouble(String key, double value);

    /**
     * Returns 0 if the key does not exist.
     */
    double getDouble(String key);

    double getDouble(String key, double defValue);

    void putString(String key, @Nullable String value);

    /**
     * Returns null if the key does not exist.
     */
    @Nullable
    String getString(String key);

    void putStringSet(String key, @Nullable Set<String> vals);

    /**
     * Returns an empty Set if the key does not exist. See
     * {@link SharedPreferences#getStringSet(String, Set)} about not modifying the returned Set.
     */
    @Nullable
    Set<String> getStringSet(String key);

    EasyEditor edit();

    void remove(String key);

    /**
     * Remove all values.
     */
    EasyPreferences clear();

    interface EasyEditor extends Editor {
        EasyEditor putBoolean(String key, boolean value);

        EasyEditor putInt(String key, int value);

        EasyEditor putLong(String key, long value);

        EasyEditor putFloat(String key, float value);

        EasyEditor putDouble(String key, double value);

        EasyEditor putString(String key, @Nullable String value);

        EasyEditor putStringSet(String key, @Nullable Set<String> values);

        EasyEditor remove(String key);

        EasyEditor clear();
    }
}
