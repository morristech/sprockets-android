/*
 * Copyright 2014-2016 pushbit <pushbit@gmail.com>
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

import android.graphics.Color;
import android.support.annotation.ColorInt;

import org.apache.commons.lang3.RandomUtils;

/**
 * Utility methods for working with colors.
 */
public class Colors {
    /* gamma encoded N/6*3(R+G+B) segments (((256÷6×N)÷255)^(1÷2.222222))×255 */
    private static final int SIX = 765;
    private static final int FIVE = 705;
    private static final int FOUR = 639;
    private static final int THREE = 561;
    private static final int TWO = 468;
    private static final int ONE = 342;

    private Colors() {
    }

    /**
     * Get a random color.
     */
    @ColorInt
    public static int random() {
        return between(0, SIX);
    }

    /**
     * Get a random color from the darkest sixth.
     */
    @ColorInt
    public static int darkest() {
        return between(0, ONE);
    }

    /**
     * Get a random color from the middle darker.
     */
    @ColorInt
    public static int darker() {
        return between(ONE, TWO);
    }

    /**
     * Get a random color from the lightest dark.
     */
    @ColorInt
    public static int dark() {
        return between(TWO, THREE);
    }

    /**
     * Get a random color from the darkest light.
     */
    @ColorInt
    public static int light() {
        return between(THREE, FOUR);
    }

    /**
     * Get a random color from the middle lighter.
     */
    @ColorInt
    public static int lighter() {
        return between(FOUR, FIVE);
    }

    /**
     * Get a random color from the lightest sixth.
     */
    @ColorInt
    public static int lightest() {
        return between(FIVE, SIX);
    }

    private static int between(int min, int max) {
        /* create three color components that will add up to be between the min and max */
        int a = RandomUtils.nextInt(Math.max(min - 510, 0), 256); // with light colors each has min
        int b = RandomUtils.nextInt(Math.max(min - a - 255, 0), Math.min(max - a, 255) + 1);
        int c = RandomUtils.nextInt(Math.max(min - a - b, 0), Math.min(max - (a + b), 255) + 1);
        switch (RandomUtils.nextInt(0, 6)) { // mix 'em up and make a color out of them
            case 0:
                return Color.rgb(a, b, c);
            case 1:
                return Color.rgb(a, c, b);
            case 2:
                return Color.rgb(b, a, c);
            case 3:
                return Color.rgb(c, a, b);
            case 4:
                return Color.rgb(b, c, a);
            case 5:
                return Color.rgb(c, b, a);
        }
        throw new RuntimeException("Random.nextInt(n) returned something other than [0,n)?!");
    }
}
