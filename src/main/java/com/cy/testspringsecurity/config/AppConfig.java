package com.cy.testspringsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mars on 2015/4/24.
 */
@Configuration
@Import(WebSecurityConfig.class)
public class AppConfig {
}
