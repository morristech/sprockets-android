/*
 * Copyright 2013-2015 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import static android.graphics.Bitmap.Config.ARGB_8888;

/**
 * Utility methods for working with Bitmaps.
 */
public class Bitmaps {
    private Bitmaps() {
    }

    /**
     * Get the number of bytes that would be used to store a bitmap with this size, in pixels, and
     * the {@link Config#ARGB_8888 ARGB_8888} (recommended) storage config.
     */
    public static int getByteCount(int width, int height) {
        return getByteCount(width, height, ARGB_8888);
    }

    /**
     * Get the number of bytes that would be used to store a bitmap with this size, in pixels, and
     * storage config.
     */
    public static int getByteCount(int width, int height, Config config) {
        int bytes = 0;
        switch (config) {
            case ALPHA_8:
                bytes = 1;
                break;
            case RGB_565:
                bytes = 2;
                break;
            case ARGB_4444:
                bytes = 2;
                break;
            case ARGB_8888:
                bytes = 4;
                break;
        }
        return width * height * bytes;
    }

    /**
     * If the bitmap is immutable, get a mutable copy of it. After a copy is created, the source
     * bitmap will be recycled. If the bitmap is already mutable, it will be returned.
     *
     * @return null if a copy could not be made
     */
    public static Bitmap mutable(Bitmap source) {
        if (source.isMutable()) {
            return source;
        }
        Config config = source.getConfig();
        Bitmap bm = source.copy(config != null ? config : ARGB_8888, true);
        if (bm != null) {
            source.recycle();
        }
        return bm;
    }
}
