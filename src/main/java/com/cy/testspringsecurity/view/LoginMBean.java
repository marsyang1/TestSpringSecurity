package com.cy.testspringsecurity.view;

import org.omnifaces.util.Faces;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mars on 2015/4/24.
 */
@ManagedBean
@RequestScoped
public class LoginMBean {

    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return user.getUsername();
        } else {
            return "";
        }
    }

    public String getUserIdByRequest() {
        HttpServletRequest request = Faces.getRequest();
        return request.getUserPrincipal().getName();
    }

}
