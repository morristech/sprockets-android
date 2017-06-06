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

package net.sf.sprockets.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import net.sf.sprockets.R;
import net.sf.sprockets.net.Uris;

import java.util.List;

import static android.content.Intent.ACTION_SENDTO;
import static android.content.Intent.ACTION_VIEW;
import static android.content.pm.PackageManager.MATCH_DEFAULT_ONLY;

/**
 * Utility methods for working with Intents.
 */
public class Intents {
    private Intents() {
    }

    /**
     * True if the Intent can be resolved to an Activity.
     */
    public static boolean hasActivity(Context context, Intent intent) {
        return context.getPackageManager().resolveActivity(intent, MATCH_DEFAULT_ONLY) != null;
    }

    /**
     * Get an Activity Intent that launches the app's details page.
     *
     * @since 4.0.0
     */
    public static Intent appDetails(Context context) {
        return new Intent(ACTION_VIEW, Uris.appDetails(context));
    }

    /**
     * Get an Activity Intent that launches an email app with the headers. Any null or empty
     * parameters are skipped. The subject and body will be encoded.
     *
     * @since 4.0.0
     */
    public static Intent mailto(List<String> to, List<String> cc, List<String> bcc, String subject,
                                String body) {
        return new Intent(ACTION_SENDTO, Uris.mailto(to, cc, bcc, subject, body));
    }

    /**
     * Get an Activity Intent that launches a {@link PlacePicker}. If the required version of
     * Google Play services is not available, an appropriate dialog will be shown and null will be
     * returned. You can listen for the dismissal of the dialog in
     * {@link Activity#onActivityResult(int, int, Intent) onActivityResult} with requestCode
     * {@code R.id.sprockets_gmsError}.
     *
     * @since 4.0.0
     */
    @Nullable
    public static Intent placePicker(Activity a) {
        int errorCode;
        try {
            return new PlacePicker.IntentBuilder().build(a);
        } catch (GooglePlayServicesNotAvailableException e) {
            errorCode = e.errorCode;
        } catch (GooglePlayServicesRepairableException e) {
            errorCode = e.getConnectionStatusCode();
        }
        GoogleApiAvailability.getInstance()
                .showErrorDialogFragment(a, errorCode, R.id.sprockets_gmsError);
        return null;
    }
}
