package com.cy.testspringsecurity.repo;

public interface QRCodeTicketRepository {

	void createTicket(String name);

	boolean useTicket(String name);
}
