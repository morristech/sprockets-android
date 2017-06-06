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

package net.sf.sprockets.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.SignInIntentBuilder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

/**
 * Manages the authentication of users.
 *
 * @see FirebaseAuth
 * @see AuthUI
 * @since 4.0.0
 */
public interface Auth {
    @Nullable
    FirebaseUser getCurrentUser();

    void addAuthStateListener(AuthStateListener listener);

    void removeAuthStateListener(AuthStateListener listener);

    /**
     * Get an Intent to sign in to the identity provider.
     *
     * @param providerId must be one of the {@link AuthUI} {@code *_PROVIDER} constants
     */
    Intent createSignInIntent(String providerId);

    SignInIntentBuilder createSignInIntentBuilder();

    Task<ProviderQueryResult> fetchProvidersForEmail(String email);

    Task<AuthResult> createUserWithEmailAndPassword(String email, String password);

    Task<AuthResult> signInAnonymously();

    Task<AuthResult> signInWithCredential(AuthCredential credential);

    Task<AuthResult> signInWithEmailAndPassword(String email, String password);

    Task<AuthResult> signInWithCustomToken(String token);

    void signOut();

    Task<Void> signOut(@NonNull FragmentActivity a);

    Task<Void> delete(@NonNull FragmentActivity a);

    Task<Void> sendPasswordResetEmail(String email);

    Task<String> verifyPasswordResetCode(String code);

    Task<Void> confirmPasswordReset(String code, String newPassword);

    Task<ActionCodeResult> checkActionCode(String code);

    Task<Void> applyActionCode(String code);
}
