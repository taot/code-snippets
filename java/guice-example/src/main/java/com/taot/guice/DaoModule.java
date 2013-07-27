package com.taot.guice;

import com.google.inject.AbstractModule;
import com.taot.guice.dao.AccountDao;
import com.taot.guice.dao.impl.AccountDaoImpl;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDao.class).to(AccountDaoImpl.class);
    }
}
