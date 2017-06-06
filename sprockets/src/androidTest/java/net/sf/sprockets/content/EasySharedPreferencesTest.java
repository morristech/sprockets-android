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

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EasySharedPreferencesTest extends SprocketsTest {
    @Test
    public void testPutGetRemove() {
        EasySharedPreferences prefs = new EasySharedPreferences(targetContext).clear();
        int i = 3;
        long l = 5L;
        float f = 7.11f;
        double d = 13.171923;
        String s = "test string";
        Set<String> ss = Collections.singleton("test string set");

        prefs.putBoolean("test_bool", true);
        prefs.putInt("test_int", i);
        prefs.putLong("test_long", l);
        prefs.putFloat("test_float", f);
        prefs.putDouble("test_double", d);
        prefs.putString("test_string", s);
        prefs.putStringSet("test_string_set", ss);

        assertTrue(prefs.getBoolean("test_bool"));
        assertEquals(i, prefs.getInt("test_int"));
        assertEquals(l, prefs.getLong("test_long"));
        assertEquals(f, prefs.getFloat("test_float"), 0.0f);
        assertEquals(d, prefs.getDouble("test_double"), 0.0);
        assertEquals(s, prefs.getString("test_string"));
        assertEquals(ss, prefs.getStringSet("test_string_set"));

        prefs.remove("test_int");
        assertEquals(89, prefs.getInt("test_int", 89));
        prefs.remove("test_double");
        assertEquals(97.101103, prefs.getDouble("test_double", 97.101103), 0.0);
    }
}
