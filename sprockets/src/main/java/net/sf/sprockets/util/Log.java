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

import net.sf.sprockets.lang.Classes;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;

/**
 * Logging interface modelled after Android's {@link android.util.Log Log}. Implementations may
 * send the log messages to a variety of destinations.
 *
 * @since 4.0.0
 */
public abstract class Log {
    /**
     * Send an ERROR log message.
     */
    public void e(Object src, String msg) {
        e(src, msg, null);
    }

    /**
     * Send an ERROR log message and log the exception.
     */
    public void e(Object src, String msg, @Nullable Throwable thrown) {
        maybeLog(ERROR, src, msg, thrown);
    }

    /**
     * Send a WARN log message.
     */
    public void w(Object src, String msg) {
        w(src, msg, null);
    }

    /**
     * Send a WARN log message and log the exception.
     */
    public void w(Object src, String msg, @Nullable Throwable thrown) {
        maybeLog(WARN, src, msg, thrown);
    }

    /**
     * Send an INFO log message.
     */
    public void i(Object src, String msg) {
        i(src, msg, null);
    }

    /**
     * Send an INFO log message and log the exception.
     */
    public void i(Object src, String msg, @Nullable Throwable thrown) {
        maybeLog(INFO, src, msg, thrown);
    }

    /**
     * Send a DEBUG log message.
     */
    public void d(Object src, String msg) {
        d(src, msg, null);
    }

    /**
     * Send a DEBUG log message and log the exception.
     */
    public void d(Object src, String msg, @Nullable Throwable thrown) {
        maybeLog(DEBUG, src, msg, thrown);
    }

    /**
     * Send a VERBOSE log message.
     */
    public void v(Object src, String msg) {
        v(src, msg, null);
    }

    /**
     * Send a VERBOSE log message and log the exception.
     */
    public void v(Object src, String msg, @Nullable Throwable thrown) {
        maybeLog(VERBOSE, src, msg, thrown);
    }

    /**
     * True if a log message at the level should be logged.
     *
     * @param level one of the Android {@link android.util.Log Log} constants
     */
    public abstract boolean isLoggable(int level);

    /**
     * Send the log message at the level and log the exception.
     * <p>
     * <strong>Note:</strong> This will only be called if the level
     * {@link #isLoggable(int) is loggable}.
     * </p>
     *
     * @param level one of the Android {@link android.util.Log Log} constants
     */
    protected abstract void log(int level, Class<?> src, String msg, @Nullable Throwable thrown);

    private void maybeLog(int level, Object src, String msg, Throwable thrown) {
        if (isLoggable(level)) {
            log(level, Classes.getNamedClass(src), msg, thrown);
        }
    }

    /**
     * Get the name of the level.
     *
     * @param level one of the Android {@link android.util.Log Log} constants
     */
    public static String getLevelName(int level) {
        switch (level) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            default:
                return "?";
        }
    }
}
