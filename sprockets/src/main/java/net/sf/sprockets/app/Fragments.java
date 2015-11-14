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

package net.sf.sprockets.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static android.os.Bundle.EMPTY;

/**
 * Utility methods for working with Fragments.
 */
public class Fragments {
    private Fragments() {
    }

    /**
     * Get the arguments for the Fragment. If the Fragment doesn't have arguments and it isn't
     * attached to an Activity, a new Bundle will be set as its arguments. Otherwise an empty (and
     * immutable) Bundle will be returned.
     */
    public static Bundle arguments(Fragment frag) {
        Bundle args = frag.getArguments();
        if (args == null) {
            if (!frag.isAdded()) {
                args = new Bundle();
                frag.setArguments(args);
            } else {
                args = EMPTY;
            }
        }
        return args;
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_OPEN TRANSIT_FRAGMENT_OPEN} transition.
     *
     * @return null if the FragmentManager is not available
     */
    public static FragmentTransaction open(Activity activity) {
        return open(activity.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_OPEN TRANSIT_FRAGMENT_OPEN} transition.
     *
     * @return null if the FragmentManager is not available
     */
    public static FragmentTransaction open(Fragment frag) {
        return open(frag.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_OPEN TRANSIT_FRAGMENT_OPEN} transition.
     *
     * @return null if the FragmentManager is null
     */
    public static FragmentTransaction open(FragmentManager fm) {
        return transit(fm, TRANSIT_FRAGMENT_OPEN);
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is not available
     */
    public static FragmentTransaction close(Activity activity) {
        return close(activity.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is not available
     */
    public static FragmentTransaction close(Fragment frag) {
        return close(frag.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is null
     */
    public static FragmentTransaction close(FragmentManager fm) {
        return transit(fm, TRANSIT_FRAGMENT_CLOSE);
    }

    /**
     * Begin a transaction that uses the transition.
     *
     * @return null if the FragmentManager is null
     */
    private static FragmentTransaction transit(FragmentManager fm, int transit) {
        return fm != null ? fm.beginTransaction().setTransition(transit) : null;
    }
}
