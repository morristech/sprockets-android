/*
 * Copyright 2013-2017 pushbit <pushbit@gmail.com>
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
import android.location.Location;

import com.google.android.gms.location.LocationRequest;

import net.sf.sprockets.gms.location.LocationProvider;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.subjects.MaybeSubject;

import static com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;

/**
 * Provides the current device location or null if a location is not available.
 *
 * @since 4.0.0
 */
public class LocationLoader extends AsyncLoader<Location> {
    /**
     * Request location with
     * {@link LocationRequest#PRIORITY_BALANCED_POWER_ACCURACY PRIORITY_BALANCED_POWER_ACCURACY}.
     */
    public static final int DEFAULT_PRIORITY = PRIORITY_BALANCED_POWER_ACCURACY;

    private final LocationProvider mProvider;
    private int mPriority = DEFAULT_PRIORITY;
    private int[] mPriorities;
    private volatile MaybeSubject<Location> mCancel;

    @Inject
    public LocationLoader(Context context, LocationProvider provider) {
        super(context);
        mProvider = provider;
    }

    /**
     * Request location with the priority.
     *
     * @param priority must be one of the {@link LocationRequest} PRIORITY constants
     * @see #DEFAULT_PRIORITY
     */
    public LocationLoader priority(int priority) {
        mPriority = priority;
        mPriorities = null;
        return this;
    }

    /**
     * Request location with any of the priorities.
     *
     * @param priorities must be any of the {@link LocationRequest} PRIORITY constants
     */
    public LocationLoader priorities(int... priorities) {
        mPriorities = priorities;
        return this;
    }

    @Override
    public Location loadInBackground() {
        mCancel = MaybeSubject.create();
        ArrayList<MaybeSource<Location>> sources;
        if (mPriorities != null) {
            sources = new ArrayList<>(mPriorities.length + 1);
            for (int priority : mPriorities) {
                sources.add(mProvider.getCurrentLocation(priority));
            }
        } else {
            sources = new ArrayList<>(2);
            sources.add(mProvider.getCurrentLocation(mPriority));
        }
        sources.add(mCancel);
        Location location = Maybe.amb(sources).blockingGet();
        mCancel = null;
        return location;
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        if (mCancel != null) {
            mCancel.onComplete();
        }
    }
}
