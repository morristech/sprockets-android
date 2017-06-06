/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.view.inputmethod;

import android.view.View;

import net.sf.sprockets.content.Managers;

/**
 * Utility methods for working with InputMethods.
 */
public class InputMethods {
    private InputMethods() {
    }

    /**
     * Show the current input method for the focused View which can receive input.
     */
    public static void show(View view) {
        view.postDelayed(() -> Managers.inputMethod(view.getContext()).showSoftInput(view, 0),
                300L); // give InputMethodManager some time to recognise that the View is focused
    }

    /**
     * Hide the input method for the View's window.
     */
    public static void hide(View view) {
        Managers.inputMethod(view.getContext()).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
