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

package net.sf.sprockets.app.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.Nullable;

import net.sf.sprockets.app.Fragments;

/**
 * Provides shortcuts to the containing Activity and a dialog identifier.
 */
public abstract class SprocketsDialogFragment extends DialogFragment {
    private static final String DIALOG_ID = SprocketsDialogFragment.class.getName() + ".dialog_id";

    /**
     * Shortcut to {@link #getActivity()}.
     */
    @Nullable protected Activity a;

    /**
     * Shortcut to {@link #getActivity()}, casting to your assignment type.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends Activity> T a() {
        return (T) a;
    }

    /**
     * Identify this dialog instance. Can only be called before the dialog is shown (or otherwise
     * attached to an Activity).
     *
     * @throws UnsupportedOperationException if called after the Fragment is attached to an
     *                                       Activity
     * @since 4.0.0
     */
    public SprocketsDialogFragment setDialogId(int id) {
        Fragments.arguments(this).putInt(DIALOG_ID, id);
        return this;
    }

    /**
     * Get this dialog instance's identifier.
     *
     * @return 0 if an identifier has not been set
     * @since 4.0.0
     */
    public int getDialogId() {
        return Fragments.arguments(this).getInt(DIALOG_ID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    @Override
    public void onDetach() {
        a = null;
        super.onDetach();
    }

    /**
     * Get an {@link OnClickListener OnClickListener} that forwards clicks to the
     * OnDialogClickListener.
     *
     * @since 4.0.0
     */
    protected OnClickListener onClickListener(OnDialogClickListener listener) {
        return (dialog, which) -> listener.onClick(this, which);
    }

    /**
     * Notified when a button in the dialog is clicked.
     *
     * @since 4.0.0
     */
    public interface OnDialogClickListener {
        /**
         * A button in the dialog was clicked.
         *
         * @param which button that was clicked, one of the {@link DialogInterface} BUTTON_*
         *              constants
         */
        void onClick(SprocketsDialogFragment dialog, int which);
    }
}
