/*
 * Copyright 2013-2017 pushbit <pushbit@gmail.com>
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

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Backed by a {@link SharedPreferences} instance.
 *
 * @since 4.0.0
 */
@Singleton
public class EasySharedPreferences implements EasyPreferences {
    private final SharedPreferences mPrefs;

    /**
     * Use the default SharedPreferences.
     */
    @Inject
    public EasySharedPreferences(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Use the named SharedPreferences.
     */
    public EasySharedPreferences(Context context, String name) {
        mPrefs = context.getSharedPreferences(name, MODE_PRIVATE);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mPrefs.getBoolean(key, defValue);
    }

    @Override
    public void putInt(String key, int value) {
        edit().putInt(key, value).apply();
    }

    @Override
    public int getInt(String key) {
        return getInt(key, 0);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    @Override
    public void putLong(String key, long value) {
        edit().putLong(key, value).apply();
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mPrefs.getLong(key, defValue);
    }

    @Override
    public void putFloat(String key, float value) {
        edit().putFloat(key, value).apply();
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0.0f);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mPrefs.getFloat(key, defValue);
    }

    @Override
    public void putDouble(String key, double value) {
        edit().putDouble(key, value).apply();
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    @Override
    public double getDouble(String key, double defValue) {
        return mPrefs.contains(key) ? Double.longBitsToDouble(mPrefs.getLong(key, 0L)) : defValue;
    }

    @Override
    public void putString(String key, @Nullable String value) {
        edit().putString(key, value).apply();
    }

    @Override
    @Nullable
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    @Nullable
    public String getString(String key, @Nullable String defValue) {
        return mPrefs.getString(key, defValue);
    }

    @Override
    public void putStringSet(String key, @Nullable Set<String> vals) {
        edit().putStringSet(key, vals).apply();
    }

    @Override
    @Nullable
    public Set<String> getStringSet(String key) {
        return getStringSet(key, Collections.emptySet());
    }

    @Override
    @Nullable
    public Set<String> getStringSet(String key, @Nullable Set<String> defValue) {
        return mPrefs.getStringSet(key, defValue);
    }

    @Override
    public Map<String, ?> getAll() {
        return mPrefs.getAll();
    }

    @Override
    public boolean contains(String key) {
        return mPrefs.contains(key);
    }

    @Override
    public EasyEditor edit() {
        return new EasySharedPreferencesEditor(mPrefs.edit());
    }

    @Override
    public void remove(String key) {
        edit().remove(key).apply();
    }

    @Override
    public EasySharedPreferences clear() {
        edit().clear().apply();
        return this;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener lis) {
        mPrefs.registerOnSharedPreferenceChangeListener(lis);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener lis) {
        mPrefs.unregisterOnSharedPreferenceChangeListener(lis);
    }

    /**
     * Get the name used for storing default shared preferences.
     */
    public static String getDefaultName(Context context) { // deprecate/remove when min API >= 24
        /* copied from PreferenceManager.getDefaultSharedPreferencesName */
        return context.getPackageName() + "_preferences";
    }

    private static class EasySharedPreferencesEditor implements EasyEditor {
        private final Editor mEditor;

        EasySharedPreferencesEditor(Editor editor) {
            mEditor = editor;
        }

        @Override
        public EasyEditor putBoolean(String key, boolean value) {
            mEditor.putBoolean(key, value);
            return this;
        }

        @Override
        public EasyEditor putInt(String key, int value) {
            mEditor.putInt(key, value);
            return this;
        }

        @Override
        public EasyEditor putLong(String key, long value) {
            mEditor.putLong(key, value);
            return this;
        }

        @Override
        public EasyEditor putFloat(String key, float value) {
            mEditor.putFloat(key, value);
            return this;
        }

        @Override
        public EasyEditor putDouble(String key, double value) {
            mEditor.putLong(key, Double.doubleToLongBits(value));
            return this;
        }

        @Override
        public EasyEditor putString(String key, @Nullable String value) {
            mEditor.putString(key, value);
            return this;
        }

        @Override
        public EasyEditor putStringSet(String key, @Nullable Set<String> values) {
            mEditor.putStringSet(key, values);
            return this;
        }

        @Override
        public EasyEditor remove(String key) {
            mEditor.remove(key);
            return this;
        }

        @Override
        public EasyEditor clear() {
            mEditor.clear();
            return this;
        }

        @Override
        public void apply() {
            mEditor.apply();
        }

        @Override
        public boolean commit() {
            return mEditor.commit();
        }
    }
}
