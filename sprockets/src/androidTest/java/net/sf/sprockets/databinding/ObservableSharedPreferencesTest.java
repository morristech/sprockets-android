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

import android.annotation.SuppressLint;
import android.databinding.Observable.OnPropertyChangedCallback;

import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.content.EasySharedPreferences;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@SuppressLint("ApplySharedPref")
public class ObservableSharedPreferencesTest extends SprocketsTest {
    private final String mKey = "test_key";
    private final int mFieldId = 97;
    private EasyPreferences mPrefs;
    private ObservableSharedPreferences mObserved;
    @Mock private OnPropertyChangedCallback mCallback;

    @Before
    public void resetBefore() throws Exception {
        mPrefs = new EasySharedPreferences(targetContext).clear();
        mPrefs.edit().commit(); // block so clear doesn't trigger property change later
        mObserved = new ObservableSharedPreferences(mPrefs) {
        };
        mObserved.bind(mKey, mFieldId);
        mObserved.addOnPropertyChangedCallback(mCallback);
    }

    @Test
    public void testDirectChange() {
        int val = 5;
        mObserved.putInt(mKey, val);
        mPrefs.edit().commit(); // block to ensure pref change listener is called
        verify(mCallback).onPropertyChanged(mObserved, mFieldId);
        assertEquals(val, mObserved.getInt(mKey));
    }

    @Test
    public void testIndirectChange() {
        String val = "five";
        mPrefs.putString(mKey, val);
        mPrefs.edit().commit();
        verify(mCallback).onPropertyChanged(mObserved, mFieldId);
        assertEquals(val, mObserved.getString(mKey));
    }

    @After
    public void resetAfter() {
        mObserved.removeOnPropertyChangedCallback(mCallback);
    }
}
