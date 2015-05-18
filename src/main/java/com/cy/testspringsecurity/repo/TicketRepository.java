package com.cy.testspringsecurity.repo;

/**
 * Created by mars on 2015/5/16.
 */
public interface TicketRepository {

    void addTickey(String ticket);

    boolean useTickey(String ticketkey);
}
