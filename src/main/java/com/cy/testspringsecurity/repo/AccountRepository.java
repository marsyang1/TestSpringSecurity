package com.cy.testspringsecurity.repo;

import com.cy.testspringsecurity.domain.Account;

public interface AccountRepository {

    void addAccount(Account account);

    Account findAccountById(long id);

    Account findAccountByName(String name);

    boolean exist(String name);
}
