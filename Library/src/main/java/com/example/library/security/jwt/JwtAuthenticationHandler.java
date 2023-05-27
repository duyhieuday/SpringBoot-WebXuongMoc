package com.example.library.security.jwt;

import com.example.library.model.LoginError;
import com.example.library.security.SecurityConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationHandler implements AuthenticationFailureHandler, AuthenticationSuccessHandler {

    private final JwtCookiesManager jwtCookiesManager;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationHandler(JwtCookiesManager jwtCookiesManager, JwtTokenProvider jwtTokenProvider) {
        this.jwtCookiesManager = jwtCookiesManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Authentication authentication) throws IOException {

        String username = request.getParameter("username");

        String jwtSessionToken = jwtTokenProvider.generateToken(username, SecurityConstant.getInstance().getLoginSessionExp());
        jwtCookiesManager.setLoginSessionToken(response, jwtSessionToken, SecurityConstant.getInstance().getLoginSessionExp());

        if (request.getParameter("remember-me") != null) {
            String jwtRememberMeToken = jwtTokenProvider.generateToken(username, SecurityConstant.getInstance().getLoginRememberMeExp());
            jwtCookiesManager.setRememberMeToken(response, jwtRememberMeToken, SecurityConstant.getInstance().getLoginRememberMeExp());
        } else {
            jwtCookiesManager.clearRememberMeToken(response);
        }
        response.sendRedirect(SecurityConstant.getInstance().getLoginSuccessPage());
    }

    @Override
    public void onAuthenticationFailure(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull AuthenticationException exception) throws IOException {

        String jwtRememberMe = jwtCookiesManager.getRememberMeToken(request);
        if (jwtRememberMe != null && onAuthenticationRememberMe(request, response, jwtRememberMe)) {
            return;
        }
        LoginError error = new LoginError();
        error.setUsername(request.getParameter("username"));
        error.setError(!StringUtils.hasText(request.getParameter("password"))
                ? "Username or password is invalid!"
                : exception.getMessage());
        String redirect = SecurityConstant.getInstance().getLoginFailurePage() + "=" + LoginError.encode(error);
        response.sendRedirect(redirect);
    }

    private boolean onAuthenticationRememberMe(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, String jwtRememberMe) throws IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // the username is match && password match
            if (Objects.equals(username, jwtTokenProvider.getUserName(jwtRememberMe)) && Objects.equals(password, jwtRememberMe)) {
                Authentication auth = jwtTokenProvider.getAuthentication(jwtRememberMe);
                onAuthenticationSuccess(request, response, auth);
                return true;
            }
            return false;
        } catch (Throwable e) {
            return false;
        }
    }

}
