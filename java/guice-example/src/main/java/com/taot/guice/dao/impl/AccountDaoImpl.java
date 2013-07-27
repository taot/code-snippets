package com.taot.guice.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.taot.guice.dao.AccountDao;
import com.taot.guice.model.Account;

public class AccountDaoImpl implements AccountDao {
    
    @Inject
    private EntityManager entityManager;

    public Account findById(Long id) {
        System.out.println("AccountDaoImpl.findById");
        return entityManager.find(Account.class, id);
    }
}
