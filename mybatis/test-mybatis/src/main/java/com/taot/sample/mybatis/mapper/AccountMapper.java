package com.taot.sample.mybatis.mapper;

import java.util.List;

import com.taot.sample.mybatis.bean.Account;

public interface AccountMapper {

    Account find(Integer id);
    
    List<Account> findAll();
}
