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

package net.sf.sprockets.app.ui;

/**
 * Lifecycle methods for a presenter in the MVP (model-view-presenter) pattern.
 *
 * @since 4.0.0
 */
public interface Presenter {
    /**
     * The view is ready to be presented.
     *
     * @param viewIsRestored true if the view has been restored from a previous state
     */
    void onStart(boolean viewIsRestored);

    /**
     * The view is going away and must no longer be referenced.
     */
    void onStop();
}
