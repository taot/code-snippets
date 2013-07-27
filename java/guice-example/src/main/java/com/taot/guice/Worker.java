package com.taot.guice;

import javax.inject.Inject;

import com.taot.guice.model.Account;
import com.taot.guice.service.AccountService;

public class Worker {
    
    @Inject
    private AccountService accountService;

    public void run() {
        Account acct = accountService.findById(1L);
        System.out.println("Account: " + acct);
    }
}
