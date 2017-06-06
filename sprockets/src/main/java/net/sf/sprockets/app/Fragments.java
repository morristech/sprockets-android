/*
 * Copyright 2013-2017 pushbit <pushbit@gmail.com>
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;

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
                try {
                    frag.setArguments(args);
                } catch (IllegalStateException e) { // "attached" but not yet "added"
                    args = EMPTY;
                }
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
    @Nullable
    public static FragmentTransaction open(Activity a) {
        return open(a.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_OPEN TRANSIT_FRAGMENT_OPEN} transition.
     *
     * @return null if the FragmentManager is not available
     */
    @Nullable
    public static FragmentTransaction open(Fragment frag) {
        return open(frag.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_OPEN TRANSIT_FRAGMENT_OPEN} transition.
     *
     * @return null if the FragmentManager is null
     */
    @Nullable
    public static FragmentTransaction open(FragmentManager fm) {
        return transit(fm, TRANSIT_FRAGMENT_OPEN);
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is not available
     */
    @Nullable
    public static FragmentTransaction close(Activity a) {
        return close(a.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is not available
     */
    @Nullable
    public static FragmentTransaction close(Fragment frag) {
        return close(frag.getFragmentManager());
    }

    /**
     * Begin a transaction that uses the
     * {@link FragmentTransaction#TRANSIT_FRAGMENT_CLOSE TRANSIT_FRAGMENT_CLOSE} transition.
     *
     * @return null if the FragmentManager is null
     */
    @Nullable
    public static FragmentTransaction close(FragmentManager fm) {
        return transit(fm, TRANSIT_FRAGMENT_CLOSE);
    }

    /**
     * Begin a transaction that uses the transition.
     *
     * @return null if the FragmentManager is null
     */
    @SuppressLint("CommitTransaction")
    private static FragmentTransaction transit(FragmentManager fm, int transit) {
        return fm != null ? fm.beginTransaction().setTransition(transit) : null;
    }

    /**
     * Get the Fragment with the ID.
     *
     * @return null if the Fragment isn't found
     * @since 4.0.0
     */
    @Nullable
    public static <T extends Fragment> T findById(Activity a, @IdRes int id) {
        return findById(a.getFragmentManager(), id);
    }

    /**
     * Get the Fragment with the ID.
     *
     * @return null if the Fragment isn't found
     * @since 4.0.0
     */
    @Nullable
    public static <T extends Fragment> T findById(Fragment frag, @IdRes int id) {
        return findById(frag.getFragmentManager(), id);
    }

    /**
     * Get the Fragment with the ID.
     *
     * @return null if the Fragment isn't found
     * @since 4.0.0
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T findById(FragmentManager fm, @IdRes int id) {
        return (T) fm.findFragmentById(id);
    }
}
