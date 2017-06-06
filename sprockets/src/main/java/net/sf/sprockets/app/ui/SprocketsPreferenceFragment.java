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

package net.sf.sprockets.app.ui;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.TypedArray;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import net.sf.sprockets.R;
import net.sf.sprockets.analytics.Analytics;
import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.SprocketsComponent;
import net.sf.sprockets.content.Managers;
import net.sf.sprockets.os.Bundles;
import net.sf.sprockets.util.Elements;

import org.apache.commons.collections.primitives.ArrayIntList;

import java.util.Arrays;
import java.util.Set;

/**
 * Sets preference values as their summary for {@link EditTextPreference},
 * {@link MultiSelectListPreference}, {@link RingtonePreference}, and {@link Preference}. This can
 * already be accomplished for {@link ListPreference} by including {@code %s} in its
 * {@link ListPreference#setSummary(CharSequence) summary}. Also
 * {@link BackupManager#dataChanged() requests a backup} when a preference value changes.
 * <p>
 * <strong>Note:</strong> If you want a callback when the user changes a Preference value, override
 * {@link #onPreferenceChange(Preference, Object) onPreferenceChange}. This class already sets
 * itself as the {@link OnPreferenceChangeListener OnPreferenceChangeListener} for each Preference.
 * </p>
 * <p>XML Attributes:</p>
 * <ul>
 * <li>{@link #newInstance(int) sprockets_preferences}</li>
 * <li>{@link #newInstance(int, boolean) sprockets_logChanges}</li>
 * </ul>
 */
public class SprocketsPreferenceFragment extends PreferenceFragment
        implements OnPreferenceChangeListener {
    /**
     * Arguments key for a resource reference.
     *
     * @since 4.0.0
     */
    public static final String PREFS = SprocketsPreferenceFragment.class.getName() + ".prefs";

    /**
     * Arguments key, true to log changes in analytics.
     *
     * @since 4.0.0
     */
    public static final String LOG_CHANGES =
            SprocketsPreferenceFragment.class.getName() + ".logChanges";

    /**
     * Shortcut to {@link #getActivity()}.
     */
    @Nullable protected Activity a;

    private final Analytics mAnalytics = SprocketsComponent.get().analytics();

    /**
     * Shortcut to {@link #getActivity()}, casting to your assignment type.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Activity> T a() {
        return (T) a;
    }

    /**
     * Display the preferences.
     */
    public static SprocketsPreferenceFragment newInstance(@XmlRes int prefs) {
        return newInstance(prefs, false);
    }

    /**
     * Display the preferences and log changes in analytics.
     *
     * @since 2.6.0
     */
    public static SprocketsPreferenceFragment newInstance(@XmlRes int prefs, boolean logChanges) {
        SprocketsPreferenceFragment frag = new SprocketsPreferenceFragment();
        Bundle args = Fragments.arguments(frag);
        args.putInt(PREFS, prefs);
        args.putBoolean(LOG_CHANGES, logChanges);
        return frag;
    }

    @Override
    public void onInflate(Activity a, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(a, attrs, savedInstanceState);
        TypedArray ar = a.obtainStyledAttributes(attrs, R.styleable.SprocketsPreferenceFragment);
        Bundle args = Fragments.arguments(this);
        args.putInt(PREFS,
                ar.getResourceId(R.styleable.SprocketsPreferenceFragment_sprockets_preferences, 0));
        args.putBoolean(LOG_CHANGES,
                ar.getBoolean(R.styleable.SprocketsPreferenceFragment_sprockets_logChanges, false));
        ar.recycle();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int prefs = Fragments.arguments(this).getInt(PREFS);
        if (prefs > 0) {
            addPreferencesFromResource(prefs);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferenceScreen screen = getPreferenceScreen();
        for (int i = 0, count = screen.getPreferenceCount(); i < count; i++) {
            Preference pref = screen.getPreference(i);
            setSummary(pref);
            pref.setOnPreferenceChangeListener(this);
        }
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(mGlobalChangeListener);
    }

    /**
     * Sets the preference value as its summary when it is changed by the user or anything else.
     */
    private final OnSharedPreferenceChangeListener mGlobalChangeListener = (prefs, key) -> {
        Preference pref = findPreference(key);
        if (pref != null) {
            setSummary(pref);
        }
    };

    /**
     * Set the preference's value(s) as its summary.
     */
    protected void setSummary(Preference pref) {
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        if (pref instanceof RingtonePreference) {
            String val = prefs.getString(pref.getKey(), null);
            if (!TextUtils.isEmpty(val)) {
                Ringtone tone = RingtoneManager.getRingtone(a, Uri.parse(val));
                if (tone != null) {
                    pref.setSummary(tone.getTitle(a));
                }
            } else {
                pref.setSummary(R.string.none);
            }
        } else if (pref instanceof MultiSelectListPreference) {
            Set<String> vals = prefs.getStringSet(pref.getKey(), null);
            if (vals != null) {
                if (!vals.isEmpty()) {
                    MultiSelectListPreference multi = (MultiSelectListPreference) pref;
                    ArrayIntList indexList = new ArrayIntList(vals.size());
                    for (String val : vals) { // find selected entry indexes
                        int i = multi.findIndexOfValue(val);
                        if (i >= 0) {
                            indexList.add(i);
                        }
                    }
                    int[] indexes = indexList.toArray();
                    Arrays.sort(indexes); // to display in same order as dialog
                    pref.setSummary(TextUtils.join(getString(R.string.delimiter),
                            Elements.slice(multi.getEntries(), indexes)));
                } else {
                    pref.setSummary(R.string.none);
                }
            }
        } else if (pref instanceof EditTextPreference) {
            pref.setSummary(prefs.getString(pref.getKey(), null));
        } else if (pref.getClass().equals(Preference.class)) {
            String val = prefs.getString(pref.getKey(), null);
            if (!TextUtils.isEmpty(val)) { // don't clear existing summary
                pref.setSummary(val);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        if (pref.hasKey()) {
            Managers.backup(a).dataChanged();
            if (Fragments.arguments(this).getBoolean(LOG_CHANGES)) {
                mAnalytics.logEvent("set_" + pref.getKey(),
                        Bundles.of("pref_value", newValue.toString()));
            }
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(mGlobalChangeListener);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        a = null;
        super.onDetach();
    }
}
