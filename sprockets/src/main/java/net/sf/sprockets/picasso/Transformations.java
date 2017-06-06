/*
 * Copyright 2016 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Utility methods for working with Transformations.
 *
 * @since 4.0.0
 */
public class Transformations {
    private static CropCircleTransformation sCircle;

    private Transformations() {
    }

    /**
     * Get a cached instance.
     */
    public static CropCircleTransformation circle() {
        if (sCircle == null) {
            sCircle = new CropCircleTransformation();
        }
        return sCircle;
    }
}
