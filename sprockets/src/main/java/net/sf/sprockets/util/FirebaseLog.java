/*
 * Copyright 2016-2017 pushbit <pushbit@gmail.com>
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

import net.sf.sprockets.crash.Crash;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.util.Log.WARN;

/**
 * Sends WARN and higher priority log messages to Firebase Crash Reporting.
 *
 * @since 4.0.0
 */
@Singleton
@SuppressWarnings("ThrowableInstanceNeverThrown")
public class FirebaseLog extends Log {
    private static final Warn sWarn = new Warn();
    private static final Error sError = new Error();

    private final Crash mCrash;

    @Inject
    public FirebaseLog(Crash crash) {
        mCrash = crash;
    }

    @Override
    public boolean isLoggable(int level) {
        return level >= WARN;
    }

    @Override
    protected void log(int level, Class<?> src, String msg, @Nullable Throwable thrown) {
        if (thrown == null) {
            thrown = level == WARN ? sWarn : sError;
        }
        mCrash.log(getLevelName(level).charAt(0) + "/" + src.getSimpleName() + ": " + msg);
        mCrash.report(thrown);
    }

    private static class Warn extends Throwable {
    }

    private static class Error extends Throwable {
    }
}
