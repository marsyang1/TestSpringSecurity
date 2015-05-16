package com.cy.testspringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by mars on 2015/4/24.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "USER")
                .and().withUser("user").password("user").roles("USER");
    }


    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity
                .ignoring()
                        // All of Spring Security will ignore the requests
                .antMatchers("/resources/**")
                .antMatchers("/javax.faces.resource/**")
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/images/**")
                .antMatchers("/js/**")
                .antMatchers("/template/**")
                .antMatchers("/error/**")
                .antMatchers("/webresource/**")
        ;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin()
                .loginProcessingUrl("/j_security_check").defaultSuccessUrl("/welcomePrimefaces.xhtml")
                .and().logout().logoutSuccessUrl("/index.xhtml")
                .and().authorizeRequests()
                .antMatchers("/index*").anonymous()
                .and().authorizeRequests()
                .antMatchers("/secret/*.xhtml").hasAnyRole("ADMIN")
                .antMatchers("/**/*.xhtml", "/**/*.html", "/**/*.jsf").fullyAuthenticated()
        ;
    }


}