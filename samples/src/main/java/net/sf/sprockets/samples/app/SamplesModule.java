package net.sf.sprockets.samples.app;

import net.sf.sprockets.samples.app.ui.TestActivity;
import net.sf.sprockets.samples.app.ui.UserHeaderNavigationActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class SamplesModule {
    @ContributesAndroidInjector
    abstract TestActivity testActivityInjector();

    @ContributesAndroidInjector
    abstract UserHeaderNavigationActivity userHeaderNavigationActivityInjector();
}
