package com.taot.guice.module;

import com.google.inject.AbstractModule;
import com.taot.guice.dao.AccountDao;
import com.taot.guice.dao.impl.AccountDaoImpl;
import com.taot.guice.service.AccountService;
import com.taot.guice.service.impl.AccountServiceImpl;

public class AccountModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
    }
}
