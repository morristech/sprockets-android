/*
 * Copyright 2015-2017 pushbit <pushbit@gmail.com>
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

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButton.Behavior;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>
 * Scrolls the {@link FloatingActionButton} that it is applied to off and on screen in sync with a
 * sibling {@link AppBarLayout}.
 * </p>
 * <pre>{@code
 * <android.support.design.widget.FloatingActionButton
 *     ...
 *     app:layout_behavior="@string/sprockets_appbar_floating_action_button_behavior"/>
 * }</pre>
 * <p>
 * <strong>Note:</strong> The sibling AppBarLayout must have an {@code id} or the
 * FloatingActionButton will not be visible.
 * </p>
 *
 * @since 4.0.0
 */
public class AppBarFloatingActionButtonBehavior extends Behavior {
    public AppBarFloatingActionButtonBehavior() {
    }

    public AppBarFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child,
                                   View dependency) {
        return super.layoutDependsOn(parent, child, dependency) ||
                dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child,
                                          View dependency) {
        if (dependency instanceof AppBarLayout) {
            int childTotal = ((View) child.getParent()).getHeight() - child.getTop();
            float depProgress = Math.abs(dependency.getY()) / dependency.getHeight();
            child.setTranslationY(childTotal * depProgress);
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
