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

package net.sf.sprockets.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.content.EasyPreferences.EasyEditor;
import net.sf.sprockets.test.SprocketsTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static net.sf.sprockets.preference.Keys.APP_VERSION_CODE;
import static net.sf.sprockets.preference.Keys.APP_VERSION_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class SharedPreferencesApplicationVersionTest extends SprocketsTest {
    @Mock private Context mContext;
    @Mock private EasyPreferences mPrefs;
    private PackageInfo mPackage;
    @Mock private EasyEditor mEditor;

    @Before
    @SuppressWarnings("WrongConstant")
    @SuppressLint("CommitPrefEdits")
    public void resetBefore() throws NameNotFoundException {
        PackageManager packages = mock(PackageManager.class);
        when(mContext.getPackageManager()).thenReturn(packages);
        mPackage = new PackageInfo();
        when(packages.getPackageInfo(any(), anyInt())).thenReturn(mPackage);
        when(mPrefs.edit()).thenReturn(mEditor);
        when(mEditor.putInt(anyString(), anyInt())).thenReturn(mEditor);
        when(mEditor.putString(anyString(), anyString())).thenReturn(mEditor);
    }

    @Test
    public void testFirstVersion() {
        testNewVersion(0, null, 1, "1.0.0");
    }

    @Test
    public void testSecondVersion() {
        testNewVersion(1, "1.0.0", 2, "2.0.0");
    }

    private void testNewVersion(int oldCode, String oldName, int newCode, String newName) {
        SharedPreferencesApplicationVersion version = version(oldCode, oldName, newCode, newName);
        assertTrue(version.isNew());
        verify(mEditor).putInt(APP_VERSION_CODE, mPackage.versionCode);
        verify(mEditor).putString(APP_VERSION_NAME, mPackage.versionName);
        verify(mEditor).apply();
    }

    @Test
    public void testSameVersion() {
        SharedPreferencesApplicationVersion version = version(2, "2.0.0", 2, "2.0.0");
        assertFalse(version.isNew());
        verifyZeroInteractions(mEditor);
    }

    @Test
    public void testResetVersion() {
        SharedPreferencesApplicationVersion version = version(0, null, 1, "1.0.0");
        version.resetPreviousVersion();
        assertFalse(version.isNew());
        assertEquals(version.getVersionCode(), version.getPreviousVersionCode());
        assertEquals(version.getVersionName(), version.getPreviousVersionName());
    }

    private SharedPreferencesApplicationVersion version(int oldCode, String oldName, int newCode,
                                                        String newName) {
        when(mPrefs.getInt(eq(APP_VERSION_CODE))).thenReturn(oldCode);
        when(mPrefs.getString(eq(APP_VERSION_NAME))).thenReturn(oldName);
        mPackage.versionCode = newCode;
        mPackage.versionName = newName;
        return new SharedPreferencesApplicationVersion(mContext, mPrefs);
    }
}
