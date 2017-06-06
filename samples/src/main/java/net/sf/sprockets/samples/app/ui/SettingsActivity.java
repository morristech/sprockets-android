package net.sf.sprockets.samples.app.ui;

import android.os.Bundle;

import net.sf.sprockets.app.ui.SprocketsActivity;
import net.sf.sprockets.samples.R;

public class SettingsActivity extends SprocketsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
}
