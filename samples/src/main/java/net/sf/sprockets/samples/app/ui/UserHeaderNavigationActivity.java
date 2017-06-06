package net.sf.sprockets.samples.app.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;

import net.sf.sprockets.app.Fragments;
import net.sf.sprockets.app.ui.NavigationDrawerActivity;
import net.sf.sprockets.app.ui.UserHeaderNavigationFragment;
import net.sf.sprockets.auth.Auth;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.UserHeaderNavigationBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.firebase.ui.auth.AuthUI.GOOGLE_PROVIDER;

public class UserHeaderNavigationActivity extends NavigationDrawerActivity
        implements AuthStateListener {
    UserHeaderNavigationBinding mBind;
    @Inject Auth mAuth;

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.user_header_navigation);
        setDrawerLayout(mBind.drawer);
        setSupportActionBar(mBind.toolbar.toolbar);
        mBind.signIn.setOnClickListener(
                view -> startActivityForResult(mAuth.createSignInIntent(GOOGLE_PROVIDER), 0));

        UserHeaderNavigationFragment frag = Fragments.findById(this, R.id.navigation);
        NavigationView nav = frag.getNavigationView();
        nav.inflateMenu(R.menu.user_header_navigation);
        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.signOut:
                    mAuth.signOut(this);
                    break;
            }
            mBind.drawer.closeDrawers();
            return false;
        });
        mAuth.addAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        mBind.switcher.setDisplayedChild(mAuth.getCurrentUser() == null ? 0 : 1);
    }

    @Override
    protected void onDestroy() {
        mAuth.removeAuthStateListener(this);
        super.onDestroy();
    }
}
