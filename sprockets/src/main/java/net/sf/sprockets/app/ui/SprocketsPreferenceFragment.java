/*
 * Copyright 2014-2015 pushbit <pushbit@gmail.com>
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
import android.content.Context;
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
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.analytics.Tracker;

import net.sf.sprockets.R;
import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.content.Managers;
import net.sf.sprockets.gms.analytics.Trackers;
import net.sf.sprockets.util.Elements;

import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.IntList;

import java.util.Arrays;
import java.util.Set;

import static net.sf.sprockets.gms.analytics.Trackers.event;

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
 * <li>{@link #newInstance(int) preferences}</li>
 * <li>{@link #newInstance(int, boolean) trackChanges}</li>
 * </ul>
 * <p>
 * <a href="https://github.com/pushbit/sprockets-android/blob/master/samples/src/main/res/layout/settings.xml"
 * target="_blank">Sample Usage</a>
 * </p>
 * <p>
 * <a href="https://github.com/pushbit/sprockets-android/blob/master/samples/src/main/res/xml/preferences.xml"
 * target="_blank">Sample Preferences XML</a>
 * </p>
 */
public class SprocketsPreferenceFragment extends PreferenceFragment
        implements OnPreferenceChangeListener {
    /**
     * Arguments key for a resource reference.
     */
    private static final String PREFS = SprocketsPreferenceFragment.class.getName() + ".prefs";

    /**
     * Arguments key, true to send changes to analytics.
     */
    private static final String TRACK_CHANGES =
            SprocketsPreferenceFragment.class.getName() + ".trackChanges";

    /**
     * Shortcut to {@link #getActivity()}.
     */
    protected Activity a;

    /**
     * Display the preferences.
     */
    public static SprocketsPreferenceFragment newInstance(int prefsResId) {
        return newInstance(prefsResId, false);
    }

    /**
     * Display the preferences and send changes to analytics.
     *
     * @param trackChanges if true, {@link Trackers#use(Context, Tracker) Trackers.use} must have
     *                     already been called
     * @since 2.6.0
     */
    public static SprocketsPreferenceFragment newInstance(int prefsResId, boolean trackChanges) {
        SprocketsPreferenceFragment frag = new SprocketsPreferenceFragment();
        Bundle args = Fragments.arguments(frag);
        args.putInt(PREFS, prefsResId);
        args.putBoolean(TRACK_CHANGES, trackChanges);
        return frag;
    }

    @Override
    public void onInflate(Activity a, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(a, attrs, savedInstanceState);
        TypedArray array = a.obtainStyledAttributes(attrs, R.styleable.SprocketsPreferenceFragment);
        Bundle args = Fragments.arguments(this);
        args.putInt(PREFS,
                array.getResourceId(R.styleable.SprocketsPreferenceFragment_preferences, 0));
        args.putBoolean(TRACK_CHANGES,
                array.getBoolean(R.styleable.SprocketsPreferenceFragment_trackChanges, false));
        array.recycle();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SprocketsFragment.onCreate(this, savedInstanceState);
        int prefs = Fragments.arguments(this).getInt(PREFS);
        if (prefs > 0) {
            addPreferencesFromResource(prefs);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreferenceScreen screen = getPreferenceScreen();
        int count = screen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
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
    private final OnSharedPreferenceChangeListener mGlobalChangeListener =
            new OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    Preference pref = findPreference(key);
                    if (pref != null) {
                        setSummary(pref);
                    }
                }
            };

    /**
     * Shortcut to {@link #getActivity()}, casting to your assignment type.
     */
    public <T extends Activity> T a() {
        return (T) a;
    }

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
                    IntList indexList = new ArrayIntList(vals.size());
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
        } else if (pref.getClass() == Preference.class) {
            String val = prefs.getString(pref.getKey(), null);
            if (!TextUtils.isEmpty(val)) { // don't clear existing summary
                pref.setSummary(val);
            }
        }
    }

    private String mCategory = "settings";

    /**
     * Use the category when sending changes to analytics. Default value: "settings".
     *
     * @since 2.6.0
     */
    public SprocketsPreferenceFragment setAnalyticsCategory(String category) {
        mCategory = category;
        return this;
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object newValue) {
        if (pref.hasKey()) {
            Managers.backup(a).dataChanged();
            if (Fragments.arguments(this).getBoolean(TRACK_CHANGES)) {
                event(mCategory, "set " + pref.getKey(), pref instanceof EditTextPreference
                        || pref instanceof RingtonePreference ? null : newValue.toString());
            }
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SprocketsFragment.onSaveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(mGlobalChangeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        a = null;
    }
}
