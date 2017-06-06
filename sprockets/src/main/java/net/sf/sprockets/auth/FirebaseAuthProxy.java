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
import com.firebase.ui.auth.AuthUI.IdpConfig.Builder;
import com.firebase.ui.auth.AuthUI.SignInIntentBuilder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Forwards calls to {@link FirebaseAuth} or {@link AuthUI}.
 *
 * @since 4.0.0
 */
@Singleton
public class FirebaseAuthProxy implements Auth {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Inject
    public FirebaseAuthProxy() {
    }

    @Override
    @Nullable
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void addAuthStateListener(AuthStateListener listener) {
        mAuth.addAuthStateListener(listener);
    }

    @Override
    public void removeAuthStateListener(AuthStateListener listener) {
        mAuth.removeAuthStateListener(listener);
    }

    @Override
    public Intent createSignInIntent(String providerId) {
        return createSignInIntentBuilder()
                .setProviders(Collections.singletonList(new Builder(providerId).build())).build();
    }

    @Override
    public SignInIntentBuilder createSignInIntentBuilder() {
        return AuthUI.getInstance().createSignInIntentBuilder();
    }

    @Override
    public Task<ProviderQueryResult> fetchProvidersForEmail(String email) {
        return mAuth.fetchProvidersForEmail(email);
    }

    @Override
    public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
        return mAuth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signInAnonymously() {
        return mAuth.signInAnonymously();
    }

    @Override
    public Task<AuthResult> signInWithCredential(AuthCredential credential) {
        return mAuth.signInWithCredential(credential);
    }

    @Override
    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> signInWithCustomToken(String token) {
        return mAuth.signInWithCustomToken(token);
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public Task<Void> signOut(@NonNull FragmentActivity a) {
        return AuthUI.getInstance().signOut(a);
    }

    @Override
    public Task<Void> delete(@NonNull FragmentActivity a) {
        return AuthUI.getInstance().delete(a);
    }

    @Override
    public Task<Void> sendPasswordResetEmail(String email) {
        return mAuth.sendPasswordResetEmail(email);
    }

    @Override
    public Task<String> verifyPasswordResetCode(String code) {
        return mAuth.verifyPasswordResetCode(code);
    }

    @Override
    public Task<Void> confirmPasswordReset(String code, String newPassword) {
        return mAuth.confirmPasswordReset(code, newPassword);
    }

    @Override
    public Task<ActionCodeResult> checkActionCode(String code) {
        return mAuth.checkActionCode(code);
    }

    @Override
    public Task<Void> applyActionCode(String code) {
        return mAuth.applyActionCode(code);
    }
}
