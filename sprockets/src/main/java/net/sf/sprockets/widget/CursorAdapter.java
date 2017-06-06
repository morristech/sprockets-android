/*
 * Copyright 2015 pushbit <pushbit@gmail.com>
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

package net.sf.sprockets.widget;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.ViewHolder;

import net.sf.sprockets.database.Cursors;
import net.sf.sprockets.database.EasyCursor;

import static android.provider.BaseColumns._ID;

/**
 * RecyclerView adapter for a cursor with an {@link BaseColumns#_ID _ID} column.
 *
 * @since 4.0.0
 */
public abstract class CursorAdapter<VH extends ViewHolder> extends ResourceAdapter<VH> {
    private EasyCursor mCursor;

    /**
     * Inflate the layout when creating ViewHolders.
     */
    protected CursorAdapter(@LayoutRes int layoutId) {
        super(layoutId);
        setHasStableIds(true);
    }

    /**
     * Inflate the layouts when creating ViewHolders.
     *
     * @see ResourceAdapter#ResourceAdapter(int...) ResourceAdapter(int...)
     */
    protected CursorAdapter(@LayoutRes int... layoutIds) {
        super(layoutIds);
        setHasStableIds(true);
    }

    /**
     * Use the cursor for the items.
     */
    public CursorAdapter<VH> setCursor(@Nullable Cursor cursor) {
        if (cursor != mCursor) {
            mCursor = cursor == null || cursor instanceof EasyCursor ? (EasyCursor) cursor
                    : new EasyCursor(cursor);
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * Cursor that is being used for the items.
     */
    @Nullable
    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position) != null ? mCursor.getLong(_ID) : super.getItemId(position);
    }

    @Nullable
    public EasyCursor getItem(int position) {
        return mCursor != null && Cursors.hasPosition(mCursor, position) &&
                mCursor.moveToPosition(position) ? mCursor : null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, getItem(position), position);
    }

    public abstract void onBindViewHolder(VH holder, @Nullable EasyCursor cursor, int position);
}
