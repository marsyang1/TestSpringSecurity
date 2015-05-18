package com.cy.testspringsecurity.repo;

import com.cy.testspringsecurity.utils.KeyFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class QRCodeTicketRepositoryHashImpl implements QRCodeTicketRepository {

    @Autowired
    private TicketRepository pool;

    private static final String AVAILABLE = "1";

    @Value("${qrcode.ticketExpired}")
    private int ticketExpired = 60;

    @Override
    public void createTicket(String name) {
        pool.addTickey(KeyFormatUtils.getQRcodeTickeyKey(name));
    }

    @Override
    public boolean useTicket(String name) {
        try {
            pool.removeTickey(KeyFormatUtils.getQRcodeTickeyKey(name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
