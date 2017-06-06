package net.sf.sprockets.samples.app;

import net.sf.sprockets.app.SprocketsComponent;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class, SamplesModule.class},
        dependencies = SprocketsComponent.class)
interface SamplesComponent extends AndroidInjector<SamplesApplication> {
}
