package com.taot.guice.dao.impl;

import com.taot.guice.dao.AccountDao;
import com.taot.guice.model.Account;

public class AccountDaoImpl implements AccountDao {

    public Account findById(Long id) {
        System.out.println("AccountDaoImpl.findById");
        return null;
    }

    public Account findByName(String name) {
        System.out.println("AccountDaoImpl.findByName");
        return null;
    }
}
