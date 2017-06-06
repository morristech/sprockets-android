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

package net.sf.sprockets.perf;

import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Forwards calls to {@link FirebasePerformance}.
 *
 * @since 4.0.0
 */
@Singleton
public class FirebasePerformanceProxy implements Performance {
    private final FirebasePerformance mPerf = FirebasePerformance.getInstance();

    @Inject
    FirebasePerformanceProxy() {
    }

    @Override
    public Trace newTrace(String traceName) {
        return mPerf.newTrace(traceName);
    }

    @Override
    public Trace startTrace(String traceName) {
        return FirebasePerformance.startTrace(traceName);
    }

    @Override
    public void setPerformanceCollectionEnabled(boolean enable) {
        mPerf.setPerformanceCollectionEnabled(enable);
    }

    @Override
    public boolean isPerformanceCollectionEnabled() {
        return mPerf.isPerformanceCollectionEnabled();
    }
}
