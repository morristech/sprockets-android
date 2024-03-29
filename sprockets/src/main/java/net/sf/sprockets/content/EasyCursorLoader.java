/*
 * Copyright 2014-2017 pushbit <pushbit@gmail.com>
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

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import net.sf.sprockets.content.Content.Query;
import net.sf.sprockets.database.EasyCursor;

import java.util.concurrent.Callable;

/**
 * Wraps the loaded cursor in an {@link EasyCursor}.
 */
public class EasyCursorLoader extends CursorWrapperLoader<EasyCursor> {
    private Callable<?> mCall;

    public EasyCursorLoader(Context context) {
        super(context);
    }

    public EasyCursorLoader(Context context, Query q) {
        this(context, q.uri(), q.proj(), q.sel(), q.args(), q.order());
    }

    public EasyCursorLoader(Context context, Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * {@link EasyCursor#setTag(Object) Tag} the loaded Cursor with the result of the call, which is
     * also executed on a background thread.
     *
     * @since 2.5.0
     */
    public EasyCursorLoader tagWith(Callable<?> call) {
        mCall = call;
        return this;
    }

    @Override
    protected EasyCursor wrap(Cursor cursor) {
        EasyCursor c = new EasyCursor(cursor);
        if (mCall != null) {
            try {
                c.setTag(mCall.call());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return c;
    }
}
