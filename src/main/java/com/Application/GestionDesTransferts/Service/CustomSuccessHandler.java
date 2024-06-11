package com.Application.GestionDesTransferts.Service;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        boolean isAdmin = false;
        boolean isUser = false;

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(ADMIN_ROLE)) {
                isAdmin = true;
            } else if (authority.getAuthority().equals(USER_ROLE)) {
                isUser = true;
            }
        }

        if (isAdmin) {
            response.sendRedirect("/admin-page");
        } else if (isUser) {
            response.sendRedirect("/user-page");
        } else {
            response.sendRedirect("/error");
        }
    }
}