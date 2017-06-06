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

package net.sf.sprockets.databinding;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.databinding.BaseObservable;
import android.support.v4.util.SimpleArrayMap;

import net.sf.sprockets.content.EasyPreferences;

import java.util.Set;

/**
 * <p>
 * Notifies fields when their {@link #bind(String, int) bound} preference changes. Example
 * subclass:
 * </p>
 * <pre><code>
 * public class ViewModel extends ObservableSharedPreferences {
 *     ViewModel(EasyPreferences preferences) {
 *         super(preferences);
 *         bind("foo", BR.foo); // "foo" is a preference key
 *         bind("bar", BR.bar);
 *     }
 *
 *     public void setFoo(int foo) {
 *         putInt("foo", foo);
 *     }
 *
 *    {@literal @}Bindable
 *     public int getFoo() {
 *         return getInt("foo");
 *     }
 *
 *     public void setBar(String bar) {
 *         putString("bar", bar);
 *     }
 *
 *    {@literal @}Bindable
 *     public String getBar() {
 *         return getString("bar");
 *     }
 * }
 * </code></pre>
 *
 * @since 4.0.0
 */
public abstract class ObservableSharedPreferences extends BaseObservable {
    final SimpleArrayMap<String, Integer> mFields = new SimpleArrayMap<>();
    private final EasyPreferences mPrefs;

    @SuppressWarnings("FieldCanBeLocal") // so it doesn't get GC'd
    private final OnSharedPreferenceChangeListener mListener = (prefs, key) -> {
        Integer fieldId = mFields.get(key);
        if (fieldId != null) {
            notifyPropertyChanged(fieldId);
        }
    };

    /**
     * Observe the SharedPreferences.
     */
    protected ObservableSharedPreferences(EasyPreferences preferences) {
        mPrefs = preferences;
        mPrefs.registerOnSharedPreferenceChangeListener(mListener);
    }

    /**
     * Notify the field about changes to the preference.
     *
     * @param fieldId generated BR id for the Bindable field
     */
    protected void bind(String key, int fieldId) {
        mFields.put(key, fieldId);
    }

    protected void putBoolean(String key, boolean value) {
        mPrefs.putBoolean(key, value);
    }

    protected boolean getBoolean(String key) {
        return mPrefs.getBoolean(key);
    }

    protected void putInt(String key, int value) {
        mPrefs.putInt(key, value);
    }

    protected int getInt(String key) {
        return mPrefs.getInt(key);
    }

    protected void putLong(String key, long value) {
        mPrefs.putLong(key, value);
    }

    protected long getLong(String key) {
        return mPrefs.getLong(key);
    }

    protected void putFloat(String key, float value) {
        mPrefs.putFloat(key, value);
    }

    protected float getFloat(String key) {
        return mPrefs.getFloat(key);
    }

    protected void putString(String key, String value) {
        mPrefs.putString(key, value);
    }

    protected String getString(String key) {
        return mPrefs.getString(key);
    }

    protected void putStringSet(String key, Set<String> value) {
        mPrefs.putStringSet(key, value);
    }

    protected Set<String> getStringSet(String key) {
        return mPrefs.getStringSet(key);
    }
}
