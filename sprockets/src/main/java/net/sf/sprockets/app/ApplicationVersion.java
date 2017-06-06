/*
 * Copyright 2016 pushbit <pushbit@gmail.com>
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

import android.support.annotation.Nullable;

/**
 * Tracks the application version and provides methods to check if a new version is running for the
 * first time and to get information about the current and previous versions.
 *
 * @since 4.0.0
 */
public interface ApplicationVersion {
    /**
     * Get the version of the app when it last ran or 0 if the app is being run for the first time.
     */
    int getPreviousVersionCode();

    /**
     * Get the version of the app when it last ran or null if the app is being run for the first
     * time.
     */
    @Nullable
    String getPreviousVersionName();

    /**
     * Get the current version of the app.
     */
    int getVersionCode();

    /**
     * Get the current version of the app.
     */
    String getVersionName();

    /**
     * True if the current version of the app is different from the last version that ran or the
     * app is being run for the first time.
     */
    boolean isNew();

    /**
     * Reset the previous version to be the same as the current version, so it will no longer
     * appear that a new version of the app is being run.
     */
    ApplicationVersion resetPreviousVersion();
}
