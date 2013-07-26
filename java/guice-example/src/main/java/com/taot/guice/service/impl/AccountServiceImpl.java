package com.taot.guice.service.impl;

import javax.inject.Inject;

import com.taot.guice.dao.AccountDao;
import com.taot.guice.model.Account;
import com.taot.guice.service.AccountService;

public class AccountServiceImpl implements AccountService {
    
    @Inject
    private AccountDao accountDao;

    public Account findByName(String name) {
        System.out.println("AccountServiceImpl.findByName");
        accountDao.findByName(name);
        return null;
    }
}
