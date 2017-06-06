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

package net.sf.sprockets.app.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

/**
 * Cancels an AsyncTask if the job is stopped. The AsyncTask thread will not be interrupted, so in
 * {@code doInBackground} you must regularly check {@link AsyncTask#isCancelled() isCancelled()}. If
 * the job is stopped it will be rescheduled.
 *
 * @since 4.0.0
 */
public abstract class AsyncTaskJobService extends JobService {
    private AsyncTask<?, ?, ?> mTask;

    /**
     * Execute your AsyncTask and return it.
     */
    protected abstract AsyncTask<?, ?, ?> onExecuteAsyncTask(JobParameters params);

    @Override
    public boolean onStartJob(JobParameters params) {
        mTask = onExecuteAsyncTask(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mTask != null) {
            mTask.cancel(false);
        }
        return true;
    }
}
