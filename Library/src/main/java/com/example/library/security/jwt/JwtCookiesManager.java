package com.example.library.security.jwt;


import com.sun.istack.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Component
public class JwtCookiesManager {

    public static final String JWT_LOGIN_SESSION_TOKEN_NAME = "jwt_login_session_token";
    public static final String JWT_REMEMBER_ME_TOKEN_NAME = "jwt_remember_me_token";

    @Nullable
    public String getLoginSessionToken(@NotNull HttpServletRequest request) {
        Cookie cookie = getCookie(request, JWT_LOGIN_SESSION_TOKEN_NAME);
        return cookie != null ? cookie.getValue() : null;
    }

    public void setLoginSessionToken(@NotNull HttpServletResponse response, String jwtToken, long exp) {
        setCookie(response, JWT_LOGIN_SESSION_TOKEN_NAME, jwtToken, exp);
    }

    public void clearLoginSessionToken(@NotNull HttpServletResponse response) {
        removeCookie(response, JWT_LOGIN_SESSION_TOKEN_NAME);
    }

    public String getRememberMeToken(@NotNull HttpServletRequest request) {
        Cookie cookie = getCookie(request, JWT_REMEMBER_ME_TOKEN_NAME);
        return cookie != null ? cookie.getValue() : null;
    }

    public void setRememberMeToken(@NotNull HttpServletResponse response, String rbmJwtToken, long exp) {
        setCookie(response, JWT_REMEMBER_ME_TOKEN_NAME, rbmJwtToken, exp);
    }

    public void clearRememberMeToken(@NotNull HttpServletResponse response) {
        removeCookie(response, JWT_REMEMBER_ME_TOKEN_NAME);
    }

    private void setCookie(@NotNull HttpServletResponse response, String name, String data, long exp) {
        Cookie cookie = new Cookie(name, data);
        cookie.setMaxAge((int) exp / 1000);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Nullable
    private Cookie getCookie(@NotNull HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies).filter(c -> name.equals(c.getName())).findFirst().orElse(null);
    }

    private void removeCookie(@NotNull HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
