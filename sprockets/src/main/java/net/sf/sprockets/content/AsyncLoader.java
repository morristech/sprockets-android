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

package net.sf.sprockets.content;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Completes the AsyncTaskLoader implementation. Subclasses need only implement
 * {@link #loadInBackground()} and, optionally, {@link #cancelLoadInBackground()}.
 *
 * @since 4.0.0
 */
public abstract class AsyncLoader<D> extends AsyncTaskLoader<D> {
    private D mData;

    protected AsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        }
        if (mData == null || takeContentChanged()) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(D data) {
        if (!isReset()) {
            mData = data;
            if (isStarted()) {
                super.deliverResult(data);
            }
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        mData = null;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mData=");
        writer.println(mData);
    }
}
