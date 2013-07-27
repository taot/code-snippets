package com.taot.guice;

import com.google.inject.AbstractModule;

public class InitializerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Initializer.class);
    }

}
