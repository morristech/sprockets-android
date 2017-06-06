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

import android.content.Context;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Gets settings from {@link Secure}.
 *
 * @since 4.0.0
 */
@Singleton
public class SettingsSecure extends BaseSettings implements SecureSettings {
    @Inject
    public SettingsSecure(Context context) {
        super(context);
    }

    @Override
    public int getInt(String name) throws SettingNotFoundException {
        return Secure.getInt(mCr, name);
    }

    @Override
    public int getInt(String name, int def) {
        return Secure.getInt(mCr, name, def);
    }

    @Override
    public long getLong(String name) throws SettingNotFoundException {
        return Secure.getLong(mCr, name);
    }

    @Override
    public long getLong(String name, long def) {
        return Secure.getLong(mCr, name, def);
    }

    @Override
    public float getFloat(String name) throws SettingNotFoundException {
        return Secure.getFloat(mCr, name);
    }

    @Override
    public float getFloat(String name, float def) {
        return Secure.getFloat(mCr, name, def);
    }

    @Override
    public String getString(String name) {
        return Secure.getString(mCr, name);
    }
}
