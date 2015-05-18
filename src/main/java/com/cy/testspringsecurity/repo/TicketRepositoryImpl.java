package com.cy.testspringsecurity.repo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by mars on 2015/5/16.
 */
@Component
public class TicketRepositoryImpl implements TicketRepository {

    private Cache<String, String> tickerPool = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    @Override
    public void addTickey(String ticket) {
        tickerPool.put(ticket, ticket);
    }

    @Override
    public boolean useTickey(String ticketkey) {
        return !tickerPool.getIfPresent(ticketkey).isEmpty();
    }
}
