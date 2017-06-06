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
 * Allows the background load to continue regardless of the Loader's state. When the load has
 * completed successfully, its result will be delivered for the life of the Loader. If the load
 * returns null, it will execute again when the Loader is started.
 *
 * @since 4.0.0
 */
public abstract class OneShotLoader<D> extends AsyncTaskLoader<D> {
    private D mData;
    private boolean mIsLoading;

    protected OneShotLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        } else if (!mIsLoading) {
            mIsLoading = true;
            forceLoad();
        }
    }

    @Override
    public void deliverResult(D data) {
        mData = data;
        mIsLoading = false;
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        writer.print(prefix);
        writer.print("mData=");
        writer.println(mData);
        writer.print(prefix);
        writer.print("mIsLoading=");
        writer.println(mIsLoading);
    }
}
