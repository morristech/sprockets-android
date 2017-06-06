package net.sf.sprockets.samples.app.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.samples.R;
import net.sf.sprockets.samples.databinding.AppBarFabBehaviorBinding;
import net.sf.sprockets.samples.widget.ItemNumberAdapter;

public class AppBarFabBehaviorActivity extends SprocketsActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBarFabBehaviorBinding bind =
                DataBindingUtil.setContentView(this, R.layout.app_bar_fab_behavior);
        setSupportActionBar(bind.toolbar.toolbar);
        bind.list.setLayoutManager(new LinearLayoutManager(this));
        bind.list.setAdapter(new ItemNumberAdapter());
    }
}
