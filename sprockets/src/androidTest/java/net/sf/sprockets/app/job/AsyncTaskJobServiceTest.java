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

package net.sf.sprockets.app.job;

import android.app.job.JobParameters;
import android.os.AsyncTask;

import net.sf.sprockets.test.SprocketsTest;

import org.junit.Test;
import org.mockito.Mock;

public class AsyncTaskJobServiceTest extends SprocketsTest {
    @Mock private AsyncTask<?, ?, ?> mTask;

    @Test
    public void testCancelTask() {
        AsyncTaskJobService service = new AsyncTaskJobService() {
            @Override
            protected AsyncTask<?, ?, ?> onExecuteAsyncTask(JobParameters params) {
                return mTask;
            }
        };
        service.onStopJob(null);
        // TODO_ uncomment when can mock final methods on Android
        //verify(mTask).cancel(false);
    }
}
