package com.cy.testspringsecurity.repo;

import com.cy.testspringsecurity.utils.KeyFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class QRCodeTicketRepositoryImpl implements QRCodeTicketRepository {
    @Autowired
    private JedisPool pool;

    private static final String AVAILABLE = "1";

    @Value("${qrcode.ticketExpired}")
    private int ticketExpired = 60;

    @Override
    public void createTicket(String name) {

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(KeyFormatUtils.getQRcodeTickeyKey(name), AVAILABLE);
            jedis.expire(KeyFormatUtils.getQRcodeTickeyKey(name), ticketExpired);
        } finally {
            pool.returnResource(jedis);
        }

    }

    @Override
    public boolean useTicket(String name) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            long affects = jedis.del(KeyFormatUtils.getQRcodeTickeyKey(name));
            return affects > 0;
        } finally {
            pool.returnResource(jedis);
        }
    }

}
