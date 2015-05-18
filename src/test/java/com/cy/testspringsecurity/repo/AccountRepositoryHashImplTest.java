package com.cy.testspringsecurity.repo;

import com.cy.testspringsecurity.domain.Account;
import com.cy.testspringsecurity.utils.TOTPUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by mars on 2015/5/16.
 */
public class AccountRepositoryHashImplTest {

    private AccountRepositoryHashImpl pool = new AccountRepositoryHashImpl();
    private Random random = new Random();

    @Test
    public void testAddAccount() throws Exception {
        Account account=getTempAccount("test1");
        pool.addAccount(account);
        Assert.assertTrue(pool.exist("test1"));
    }

    @Test
    public void testFindAccountById() throws Exception {
        Account account=getTempAccount("test1");
        long id = account.getId();
        pool.addAccount(account);
        Assert.assertNotNull(pool.findAccountById(id));
    }

    @Test
    public void testFindAccountByName() throws Exception {
        Account account=getTempAccount("test1");
        String name = account.getName();
        pool.addAccount(account);
        Assert.assertNotNull(pool.findAccountByName(name));
    }

    @Test
    public void testExist() throws Exception {
        Account account=getTempAccount("test1");
        pool.addAccount(account);
        Assert.assertTrue(pool.exist("test1"));
    }

    private Account getTempAccount(String name){
        Account account = new Account();
        account.setId(random.nextLong());
        account.setName(name);
        account.setPassword("1234");
        account.setSecret(TOTPUtils.generateSecret());
        return account;
    }

}