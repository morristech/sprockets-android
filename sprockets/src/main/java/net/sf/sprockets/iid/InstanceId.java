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

package net.sf.sprockets.iid;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

/**
 * Provides a unique identifier for each app instance and tokens to authenticate and authorize
 * actions.
 *
 * @see FirebaseInstanceId
 * @since 4.0.0
 */
public interface InstanceId {
    String getId();

    long getCreationTime();

    String getToken();

    String getToken(String authorizedEntity, String scope) throws IOException;

    void deleteToken(String authorizedEntity, String scope) throws IOException;

    void deleteInstanceId() throws IOException;
}
