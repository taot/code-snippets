package com.taot.guice.service;

import com.taot.guice.model.Account;

public interface AccountService {
    
    Account findByName(String name);
}
