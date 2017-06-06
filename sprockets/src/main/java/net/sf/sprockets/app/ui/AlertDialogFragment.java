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

package net.sf.sprockets.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.StringRes;

import net.sf.sprockets.app.Fragments;

import javax.inject.Inject;

/**
 * Creates an {@link AlertDialog} with the provided arguments. Attaching Activities may implement
 * {@link SprocketsDialogFragment.OnDialogClickListener OnDialogClickListener}.
 *
 * @since 4.0.0
 */
public class AlertDialogFragment extends SprocketsDialogFragment {
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String POSITIVE_BUTTON = "positive_button";
    private static final String NEUTRAL_BUTTON = "neutral_button";
    private static final String NEGATIVE_BUTTON = "negative_button";

    @Inject
    public AlertDialogFragment() {
    }

    public AlertDialogFragment title(@StringRes int title) {
        Fragments.arguments(this).putInt(TITLE, title);
        return this;
    }

    public AlertDialogFragment message(@StringRes int message) {
        Fragments.arguments(this).putInt(MESSAGE, message);
        return this;
    }

    public AlertDialogFragment positiveButton(@StringRes int text) {
        Fragments.arguments(this).putInt(POSITIVE_BUTTON, text);
        return this;
    }

    public AlertDialogFragment neutralButton(@StringRes int text) {
        Fragments.arguments(this).putInt(NEUTRAL_BUTTON, text);
        return this;
    }

    public AlertDialogFragment negativeButton(@StringRes int text) {
        Fragments.arguments(this).putInt(NEGATIVE_BUTTON, text);
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(a);
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(TITLE)) {
                b.setTitle(args.getInt(TITLE));
            }
            if (args.containsKey(MESSAGE)) {
                b.setMessage(args.getInt(MESSAGE));
            }
            OnClickListener lis = a instanceof OnDialogClickListener ? onClickListener(a()) : null;
            if (args.containsKey(POSITIVE_BUTTON)) {
                b.setPositiveButton(args.getInt(POSITIVE_BUTTON), lis);
            }
            if (args.containsKey(NEUTRAL_BUTTON)) {
                b.setNeutralButton(args.getInt(NEUTRAL_BUTTON), lis);
            }
            if (args.containsKey(NEGATIVE_BUTTON)) {
                b.setNegativeButton(args.getInt(NEGATIVE_BUTTON), lis);
            }
        }
        return b.create();
    }
}
