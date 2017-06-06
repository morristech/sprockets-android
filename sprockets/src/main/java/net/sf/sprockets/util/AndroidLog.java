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

package net.sf.sprockets.util;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.util.Log.getStackTraceString;
import static android.util.Log.println;

/**
 * Sends log messages to the Android {@link android.util.Log Log}.
 *
 * @since 4.0.0
 */
@Singleton
public class AndroidLog extends Log {
    @Inject
    public AndroidLog() {
    }

    @Override
    public boolean isLoggable(int level) {
        return true;
    }

    @Override
    protected void log(int level, Class<?> src, String msg, @Nullable Throwable thrown) {
        println(level, src.getSimpleName(), msg + '\n' + getStackTraceString(thrown));
    }
}
