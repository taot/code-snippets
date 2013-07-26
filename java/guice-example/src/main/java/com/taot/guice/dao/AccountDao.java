package com.taot.guice.dao;

import com.taot.guice.annotation.Component;
import com.taot.guice.model.Account;

@Component
public interface AccountDao {

    Account findById(Long id);
    
    Account findByName(String name);
}
