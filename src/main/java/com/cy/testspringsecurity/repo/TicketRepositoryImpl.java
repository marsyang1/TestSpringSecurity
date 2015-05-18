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

    private Cache<String, String> ticker = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    @Override
    public void addTickey(String ticket) {
        ticker.put(ticket, ticket);
    }

    @Override
    public void removeTickey(String ticket) {
        ticker.invalidate(ticket);
    }
}
