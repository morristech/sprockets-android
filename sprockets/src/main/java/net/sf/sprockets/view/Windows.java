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

package net.sf.sprockets.view;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;

/**
 * Utility methods for working with Windows.
 */
public class Windows {
    private Windows() {
    }

    /**
     * Get the {@link View#getWindowVisibleDisplayFrame(Rect) visible area} of the Activity's
     * Window. The height of screen decorations above the Window can be found in {@link Rect#top}.
     */
    public static Rect getFrame(Activity a) {
        Rect frame = new Rect();
        a.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame;
    }
}
