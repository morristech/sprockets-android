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

package net.sf.sprockets.gms.common.api;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

/**
 * Listens for {@link GoogleApiClient} connection and suspension events and reports them to any
 * provided listeners.
 *
 * @since 4.0.0
 */
public class ConnectionListener implements ConnectionCallbacks {
    private final ConnectedListener mConnected;
    private final SuspendedListener mSuspended;

    /**
     * Ignore connection and suspension events.
     */
    public ConnectionListener() {
        this(null, null);
    }

    /**
     * Notify the listener about connection events.
     */
    public ConnectionListener(ConnectedListener connected) {
        this(connected, null);
    }

    /**
     * Notify the listeners about connection and suspension events.
     */
    public ConnectionListener(ConnectedListener connected, SuspendedListener suspended) {
        mConnected = connected;
        mSuspended = suspended;
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        if (mConnected != null) {
            mConnected.onConnected(connectionHint);
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        if (mSuspended != null) {
            mSuspended.onConnectionSuspended(cause);
        }
    }

    /**
     * Notified about {@link GoogleApiClient} connection events.
     */
    public interface ConnectedListener {
        void onConnected(@Nullable Bundle connectionHint);
    }

    /**
     * Notified about {@link GoogleApiClient} connection suspension events.
     */
    public interface SuspendedListener {
        void onConnectionSuspended(int cause);
    }
}
