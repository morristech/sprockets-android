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

package net.sf.sprockets.app.ui;

import android.app.FragmentManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.support.annotation.Nullable;
import android.support.test.annotation.UiThreadTest;
import android.text.TextUtils;

import com.google.common.collect.Sets;

import net.sf.sprockets.R;
import net.sf.sprockets.analytics.Analytics;
import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.SprocketsComponent;
import net.sf.sprockets.app.ui.SprocketsPreferenceFragmentTest.TestActivity;
import net.sf.sprockets.content.EasySharedPreferences;
import net.sf.sprockets.test.ActivityTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.PreferenceMatchers.withKey;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static net.sf.sprockets.app.ui.SprocketsPreferenceFragment.LOG_CHANGES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class SprocketsPreferenceFragmentTest extends ActivityTest<TestActivity> {
    static final String RINGTONE = "ringtone";
    static final String MULTI = "multi";
    static final String EDIT = "edit";
    static final String PREF = "pref";

    private final Analytics mAnalytics = SprocketsComponent.get().analytics();

    @Before
    public void resetBefore() {
        new EasySharedPreferences(targetContext).clear();
    }

    @Test
    @UiThreadTest
    public void testActivityAvailability() {
        assertNotNull(a.mFrag.a());
        FragmentManager fm = a.getFragmentManager();
        fm.beginTransaction().remove(a.mFrag).commit();
        fm.executePendingTransactions();
        assertNull(a.mFrag.a());
    }

    @Test
    @UiThreadTest
    public void testRingtoneSummary() {
        Preference pref = a.mFrag.findPreference(RINGTONE);
        assertEquals(a.getString(R.string.none), pref.getSummary());
        pref.getEditor().putString(RINGTONE, DEFAULT_NOTIFICATION_URI.toString()).apply();
        assertEquals(RingtoneManager.getRingtone(a, DEFAULT_NOTIFICATION_URI).getTitle(a),
                pref.getSummary());
    }

    @Test
    @UiThreadTest
    public void testMultiSummary() {
        Preference pref = a.mFrag.findPreference(MULTI);
        assertTrue(TextUtils.isEmpty(pref.getSummary()));
        pref.getEditor().putStringSet(MULTI, Collections.emptySet()).apply();
        assertEquals(a.getString(R.string.none), pref.getSummary());
        pref.getEditor().putStringSet(MULTI, Sets.newHashSet("1", "3")).apply();
        assertEquals("One, Three", pref.getSummary());
    }

    @Test
    @UiThreadTest
    public void testEditSummary() {
        String val = "test text";
        Preference pref = a.mFrag.findPreference(EDIT);
        assertTrue(TextUtils.isEmpty(pref.getSummary()));
        pref.getEditor().putString(EDIT, val).apply();
        assertEquals(val, pref.getSummary());
    }

    @Test
    @UiThreadTest
    public void testPrefSummary() {
        String val = "test pref";
        Preference pref = a.mFrag.findPreference(PREF);
        assertTrue(TextUtils.isEmpty(pref.getSummary()));
        pref.getEditor().putString(PREF, val).apply();
        assertEquals(val, pref.getSummary());
    }

    @Test
    public void testLogChanges() {
        String val = "test log";
        EditTextPreference pref = (EditTextPreference) a.mFrag.findPreference(EDIT);
        onData(withKey(pref.getKey())).perform(click());
        onView(withId(pref.getEditText().getId())).inRoot(isDialog())
                .perform(replaceText(val), closeSoftKeyboard());
        onView(withId(android.R.id.button1)).inRoot(isDialog()).perform(click());
        verify(mAnalytics).logEvent(anyString(), any(Bundle.class));
        assertEquals(val, pref.getSummary());
    }

    @After
    public void resetAfter() {
        Mockito.clearInvocations(mAnalytics);
    }

    public static class TestActivity extends SprocketsActivity {
        final TestFragment mFrag = new TestFragment();

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Fragments.arguments(mFrag).putBoolean(LOG_CHANGES, true);
            getFragmentManager().beginTransaction().add(android.R.id.content, mFrag).commit();
        }
    }

    public static class TestFragment extends SprocketsPreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(a);
            screen.addPreference(ringtone());
            screen.addPreference(multi());
            screen.addPreference(edit());
            screen.addPreference(pref());
            setPreferenceScreen(screen);
        }

        private RingtonePreference ringtone() {
            RingtonePreference pref = new RingtonePreference(a);
            pref.setKey(RINGTONE);
            return pref;
        }

        private MultiSelectListPreference multi() {
            MultiSelectListPreference pref = new MultiSelectListPreference(a);
            pref.setKey(MULTI);
            pref.setEntries(new String[]{"One", "Two", "Three"});
            pref.setEntryValues(new String[]{"1", "2", "3"});
            return pref;
        }

        private EditTextPreference edit() {
            EditTextPreference pref = new EditTextPreference(a);
            pref.setKey(EDIT);
            return pref;
        }

        private Preference pref() {
            Preference pref = new Preference(a);
            pref.setKey(PREF);
            return pref;
        }
    }
}
