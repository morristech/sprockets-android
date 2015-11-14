/*
 * Copyright 2013-2015 pushbit <pushbit@gmail.com>
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

import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.util.Log;

import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.Places.Params;
import net.sf.sprockets.google.Places.Response;

import java.io.IOException;

import static net.sf.sprockets.google.Places.URL_AUTOCOMPLETE;
import static net.sf.sprockets.google.Places.URL_DETAILS;
import static net.sf.sprockets.google.Places.URL_NEARBY_SEARCH;
import static net.sf.sprockets.google.Places.URL_PHOTO;
import static net.sf.sprockets.google.Places.URL_QUERY_AUTOCOMPLETE;
import static net.sf.sprockets.google.Places.URL_RADAR_SEARCH;
import static net.sf.sprockets.google.Places.URL_TEXT_SEARCH;

/**
 * Loader that sends requests to <a href="https://developers.google.com/places/web-service/"
 * target="_blank">Google Places API</a> services and provides the responses. See {@link Places} for
 * the types of requests that can be sent and the available parameters and returned fields for each
 * one. If there is a problem communicating with the Google Places API service, null will be
 * returned.
 * <p>
 * <a href="https://github.com/pushbit/sprockets-android/blob/master/samples/src/main/java/net/sf/sprockets/sample/app/ui/GooglePlacesLoaderActivity.java"
 * target="_blank">Sample Usage</a>
 * </p>
 *
 * @param <T> the Response type of the Places method that will be called. For example, if you
 *            provide URL_NEARBY_SEARCH then the response type would be {@code List<Place>}, if
 *            URL_DETAILS then {@code Place}, and so on.
 */
public class GooglePlacesLoader<T> extends AsyncTaskLoader<Response<T>> {
    private static final String TAG = GooglePlacesLoader.class.getSimpleName();

    private final String mUrl;
    private final Params mParams;
    private final int mFields;
    private Response<T> mResp;

    /**
     * Request the URL with the params.
     *
     * @param url    one of the {@link Places} URL_* constants
     * @param params can be null and the {@link Response Response} sent to
     *               {@link LoaderCallbacks#onLoadFinished(Loader, Object) onLoadFinished} will also
     *               be null
     * @since 3.0.0
     */
    public GooglePlacesLoader(Context context, String url, Params params) {
        this(context, url, params, 0);
    }

    /**
     * Request the URL with the params and provide a response with only the specified fields.
     *
     * @param url    one of the {@link Places} URL_* constants
     * @param params can be null and the {@link Response Response} sent to
     *               {@link LoaderCallbacks#onLoadFinished(Loader, Object) onLoadFinished} will also
     *               be null
     * @param fields {@link Places}.FIELD_* bitmask of the fields to populate in the results
     * @since 3.0.0
     */
    public GooglePlacesLoader(Context context, String url, Params params, int fields) {
        super(context);
        mUrl = url;
        mParams = params;
        mFields = fields;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mResp == null) {
            forceLoad();
        } else {
            deliverResult(mResp);
        }
    }

    @Override
    public Response<T> loadInBackground() {
        if (mParams != null && !isLoadInBackgroundCanceled()) {
            try {
                switch (mUrl) {
                    case URL_NEARBY_SEARCH:
                        return (Response<T>) Places.nearbySearch(mParams, mFields);
                    case URL_TEXT_SEARCH:
                        return (Response<T>) Places.textSearch(mParams, mFields);
                    case URL_RADAR_SEARCH:
                        return (Response<T>) Places.radarSearch(mParams);
                    case URL_AUTOCOMPLETE:
                        return (Response<T>) Places.autocomplete(mParams, mFields);
                    case URL_QUERY_AUTOCOMPLETE:
                        return (Response<T>) Places.queryAutocomplete(mParams, mFields);
                    case URL_DETAILS:
                        return (Response<T>) Places.details(mParams, mFields);
                    case URL_PHOTO:
                        return (Response<T>) Places.photo(mParams);
                }
            } catch (IOException e) {
                Log.e(TAG, "problem communicating with the Google Places API service", e);
            }
        }
        return null;
    }

    @Override
    public void deliverResult(Response<T> resp) {
        mResp = resp;
        super.deliverResult(resp);
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        mResp = null;
    }
}
