package com.taot.guice;

import com.google.inject.AbstractModule;
import com.taot.guice.service.AccountService;
import com.taot.guice.service.impl.AccountServiceImpl;

public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class);
    }

}
