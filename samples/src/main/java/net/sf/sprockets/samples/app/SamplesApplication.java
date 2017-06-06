package net.sf.sprockets.samples.app;

import net.sf.sprockets.app.ApplicationVersion;
import net.sf.sprockets.app.SprocketsComponent;
import net.sf.sprockets.content.EasyPreferences;
import net.sf.sprockets.samples.R;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class SamplesApplication extends DaggerApplication {
    @Inject ApplicationVersion mVersion;
    @Inject EasyPreferences mPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mVersion.isNew()) {
            mPrefs.putString("pref", getString(R.string.some_text));
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSamplesComponent.builder().sprocketsComponent(SprocketsComponent.get())
                .build();
    }
}
