package com.ttasjwi.ssia.handlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (hasReadAuthority(authentication)) {
            response.sendRedirect("/home");
            return;
        }
        response.sendRedirect("/error");
    }

    private static boolean hasReadAuthority(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals("read"))
                .findFirst()
                .isPresent();
    }
}
