package com.cy.testspringsecurity.repo;

import com.cy.testspringsecurity.domain.Account;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
public class AccountRepositoryHashImpl implements AccountRepository {

    private Table<Long, String, Account> table = HashBasedTable.create();

    @Override
    public void addAccount(Account account) {
        table.put(account.getId(), account.getName(), account);
    }

    @Override
    public Account findAccountById(long id) {
        Map<String, Account> row = table.row(id);
        Set<String> keySet = row.keySet();
        for (String s : keySet) {
            return row.get(s);
        }
        return null;
    }

    @Override
    public Account findAccountByName(String name) {
        Map<Long, Account> row = table.column(name);
        Set<Long> keySet = row.keySet();
        for (Long s : keySet) {
            return row.get(s);
        }
        return null;
    }

    @Override
    public boolean exist(String name) {
        return table.containsColumn(name);
    }
}