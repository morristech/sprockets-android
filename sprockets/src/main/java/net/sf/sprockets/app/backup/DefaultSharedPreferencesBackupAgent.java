/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.app.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

import net.sf.sprockets.content.EasySharedPreferences;

/**
 * <p>
 * Backs up and restores the default SharedPreferences. Just add to {@code AndroidManifest.xml}.
 * </p>
 * <pre>{@code
 * <application
 *     ...
 *     android:backupAgent="net.sf.sprockets.app.backup.DefaultSharedPreferencesBackupAgent"
 *     ...>
 * }</pre>
 *
 * @since 2.4.0
 */
/* deprecate/remove when min API >= 23 */
public class DefaultSharedPreferencesBackupAgent extends BackupAgentHelper {
    @Override
    public void onCreate() {
        super.onCreate();
        String name = EasySharedPreferences.getDefaultName(this);
        addHelper(name, new SharedPreferencesBackupHelper(this, name));
    }
}
