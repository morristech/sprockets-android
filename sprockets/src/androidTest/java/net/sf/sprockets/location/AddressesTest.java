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

package net.sf.sprockets.location;

import android.location.Address;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;

import static java.util.Locale.US;
import static org.junit.Assert.assertEquals;

public class AddressesTest extends SprocketsTest {
    private final Address mAddress = new Address(US);

    public AddressesTest() {
        mAddress.setAddressLine(0, "one");
        mAddress.setAddressLine(1, "two");
        mAddress.setAddressLine(2, "three");
    }

    @Test
    public void testConcatAddressLines() {
        String expected = "one, two, three";
        assertEquals(expected, Addresses.concatAddressLines(mAddress));
    }

    @Test
    public void testConcatAddressLinesWithDelimiter() {
        String expected = "one - two - three";
        assertEquals(expected, Addresses.concatAddressLines(mAddress, " - "));
    }
}
