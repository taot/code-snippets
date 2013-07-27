package com.taot.guice.dao;

import com.taot.guice.model.Account;

public interface AccountDao {

    Account findById(Long id);
}
