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

import android.content.ContentValues;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ValuesTest extends SprocketsTest {
    @Test
    public void testPool() {
        ContentValues vals = Values.acquire();
        assertEquals(0, vals.size());
        vals.put("one", "two");
        assertTrue(Values.release(vals));
        assertSame(vals, Values.acquire());
        assertEquals(0, vals.size());
    }

    @Test
    public void testPutAll() {
        ContentValues vals =
                Values.acquire("null", null, "string", "test", "long", 3L, "int", 5, "boolean",
                        true, "double", 7.11, "float", 13.17f, "byte[]", new byte[]{0x3, 0x5},
                        "byte", (byte) 0x7, "short", (short) 11);
        assertNull(vals.get("null"));
        assertEquals("test", vals.getAsString("string"));
        assertEquals(3L, vals.getAsLong("long").longValue());
        assertEquals(5, vals.getAsInteger("int").intValue());
        assertTrue(vals.getAsBoolean("boolean"));
        assertEquals(7.11, vals.getAsDouble("double"), 0.0);
        assertEquals(13.17f, vals.getAsFloat("float"), 0.0f);
        assertArrayEquals(new byte[]{0x3, 0x5}, vals.getAsByteArray("byte[]"));
        assertEquals((byte) 0x7, vals.getAsByte("byte").byteValue());
        assertEquals((short) 11, vals.getAsShort("short").shortValue());
        assertNull(vals.get("invalid key"));
    }
}
