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

package net.sf.sprockets.provider;

import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;

/**
 * System settings that applications can read but not write.
 *
 * @see Secure
 * @since 4.0.0
 */
public interface SecureSettings {
    int getInt(String name) throws SettingNotFoundException;

    int getInt(String name, int def);

    long getLong(String name) throws SettingNotFoundException;

    long getLong(String name, long def);

    float getFloat(String name) throws SettingNotFoundException;

    float getFloat(String name, float def);

    String getString(String name);
}
