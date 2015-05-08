/*
 * Copyright 2014-2015 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.gms.common.api;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

/**
 * Connects to Google Play services and logs any failures.
 */
public abstract class Connector implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final String TAG = Connector.class.getSimpleName();

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        connectionFailed(result);
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    /**
     * Get a connected client for the API. Must not be called on the UI thread.
     *
     * @return null if connecting fails
     * @since 2.4.0
     */
    public static GoogleApiClient client(Context context, Api<? extends NotRequiredOptions> api) {
        GoogleApiClient client = new Builder(context.getApplicationContext()).addApi(api).build();
        ConnectionResult result = client.blockingConnect();
        if (result.isSuccess()) {
            return client;
        }
        connectionFailed(result);
        return null;

    }

    /**
     * Log the failure.
     */
    private static void connectionFailed(ConnectionResult result) {
        Log.e(TAG, "couldn't connect to Google Play services: "
                + GoogleApiAvailability.getInstance().getErrorString(result.getErrorCode()));
    }
}
