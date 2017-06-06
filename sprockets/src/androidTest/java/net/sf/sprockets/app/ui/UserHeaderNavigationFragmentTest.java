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

package net.sf.sprockets.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.annotation.UiThreadTest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;

import net.sf.sprockets.app.SprocketsComponent;
import net.sf.sprockets.app.ui.UserHeaderNavigationFragmentTest.TestActivity;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.test.ActivityTest;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserHeaderNavigationFragmentTest extends ActivityTest<TestActivity> {
    private final Auth mAuth = SprocketsComponent.get().auth();
    @Mock private FirebaseAuth mFbAuth;
    @Mock private FirebaseUser mUser;
    @Captor private ArgumentCaptor<AuthStateListener> mListener;

    @Test
    public void testBeforeUserLoggedIn() {
        assertNotNull(a.mFrag.getNavigationView());
        assertNotNull(a.mFrag.getHeaderBackground());
        assertNotNull(a.mFrag.getUserPhoto());
        assertNotNull(a.mFrag.getUserNameView());
        assertNotNull(a.mFrag.getUserEmailAddressView());
    }

    @Test
    @UiThreadTest
    @SuppressWarnings("ConstantConditions")
    public void testAfterUserLoggedIn() {
        String name = "test name";
        String email = "test@example.com";
        when(mUser.getDisplayName()).thenReturn(name);
        when(mUser.getEmail()).thenReturn(email);
        when(mFbAuth.getCurrentUser()).thenReturn(mUser);

        verify(mAuth).addAuthStateListener(mListener.capture());
        mListener.getValue().onAuthStateChanged(mFbAuth);
        a.mFrag.mBind.executePendingBindings();
        assertEquals(name, a.mFrag.getUserNameView().getText());
        assertEquals(email, a.mFrag.getUserEmailAddressView().getText());
    }

    public static class TestActivity extends SprocketsActivity {
        final UserHeaderNavigationFragment mFrag = new UserHeaderNavigationFragment();

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getFragmentManager().beginTransaction().add(mFrag, null).commit();
        }
    }

    @After
    public void resetAfter() {
        Mockito.clearInvocations(mAuth);
    }
}
