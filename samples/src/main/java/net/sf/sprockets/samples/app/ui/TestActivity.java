package net.sf.sprockets.samples.app.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.TestBinding;

import dagger.android.AndroidInjection;

public class TestActivity extends SprocketsActivity {
    TestBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.test);
    }
}
