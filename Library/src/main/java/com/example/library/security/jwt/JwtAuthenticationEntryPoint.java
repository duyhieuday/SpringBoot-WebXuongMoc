package com.example.library.security.jwt;

import com.example.library.security.SecurityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    private final JwtCookiesManager jwtCookiesManager;

    public JwtAuthenticationEntryPoint(JwtCookiesManager jwtCookiesManager) {
        this.jwtCookiesManager = jwtCookiesManager;
    }

    @Override
    public void commence(HttpServletRequest request, @NotNull HttpServletResponse response,
                         @NotNull AuthenticationException authException) throws IOException {
        if (jwtCookiesManager.getLoginSessionToken(request) != null) {
            LOGGER.warn("Forbidden: {}", authException.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value());
            return;
        }
        LOGGER.warn("Unauthorized error: {}", authException.getMessage());
        response.sendRedirect(SecurityConstant.getInstance().getLoginPage());
    }
}
