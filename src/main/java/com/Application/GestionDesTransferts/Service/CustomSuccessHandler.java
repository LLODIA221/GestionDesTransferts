package com.Application.GestionDesTransferts.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomSuccessHandler.class);

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        boolean isAdmin = false;
        boolean isUser = false;

        logger.info("Authentication successful for user: {}", authentication.getName());

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            logger.info("User role found: {}", authority.getAuthority());
            if (authority.getAuthority().equals(ADMIN_ROLE)) {
                isAdmin = true;
            } else if (authority.getAuthority().equals(USER_ROLE)) {
                isUser = true;
            }
        }

        if (isAdmin) {
            logger.info("Redirecting to /admin-page");
            response.sendRedirect("/admin-page");
        } else if (isUser) {
            logger.info("Redirecting to /user-page");
            response.sendRedirect("/user-page");
        } else {
            logger.warn("No valid role found, redirecting to /error");
            response.sendRedirect("/error");
        }
    }
}
