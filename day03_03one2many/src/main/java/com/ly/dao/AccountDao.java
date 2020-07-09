package com.ly.dao;

import com.ly.domain.Account;
import com.ly.domain.AccountUser;

import java.util.List;

public interface AccountDao {

    /**
     * 拆查询所有,同时获取当前账户的所属用户信息
     * @return
     */
    List<Account> findAll();


    /**
     * 查询所有账户，并且带有用户名称和地址信息
     * @return
     */
    List<AccountUser> findAllAccount();
}
