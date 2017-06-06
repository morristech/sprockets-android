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

/**
 * Utility methods for working with Addresses.
 *
 * @since 4.0.0
 */
public class Addresses {
    private Addresses() {
    }

    /**
     * Get a single line address which uses a ", " delimiter between the original lines.
     */
    public static String concatAddressLines(Address address) {
        return concatAddressLines(address, ", ");
    }

    /**
     * Get a single line address which uses the delimiter between the original lines.
     */
    public static String concatAddressLines(Address address, String delimiter) {
        StringBuilder s = new StringBuilder(256);
        for (int i = 0, max = address.getMaxAddressLineIndex(); i <= max; i++) {
            if (i > 0) {
                s.append(delimiter);
            }
            s.append(address.getAddressLine(i));
        }
        return s.toString();
    }
}
