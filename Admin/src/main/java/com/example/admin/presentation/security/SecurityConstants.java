package com.example.admin.presentation.security;

import com.example.library.security.SecurityConstant;
import com.example.library.security.SecurityConstantBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Order(1)
@Component
public class SecurityConstants {

    private static final String LOGIN_PAGE = "/admin/auth/login";
    private static final String LOGOUT_PAGE = "/admin/auth/logout";
    private static final String LOGIN_FAILURE_PAGE = "/admin/auth/login?error";
    private static final String LOGIN_SUCCESS_PAGE = "/admin/auth/redirect";

    private static final long LOGIN_SESSION_EXP = TimeUnit.MINUTES.toMillis(30);  // 30 minute
    private static final long LOGIN_REMEMBER_ME_EXP = TimeUnit.DAYS.toMillis(14); // 2 week

    private static final String[] PUBLIC_URL = new String[]{
            "/",
            "/api/**",
            "/page/**",
            "/admin/auth/**",
            "/public/**",
            "/images/**",
            "/raw/**",
    };
    private static final String[] PUBLIC_GET_URL = new String[]{
            "/error"
    };

    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @PostConstruct
    public void init(){
        SecurityConstant.init(new SecurityConstantBuilder()
                .setLoginPage(LOGIN_PAGE)
                .setLogoutPage(LOGOUT_PAGE)
                .setLoginFailurePage(LOGIN_FAILURE_PAGE)
                .setLoginSuccessPage(LOGIN_SUCCESS_PAGE)
                .setLoginSessionExp(LOGIN_SESSION_EXP)
                .setLoginRememberMeExp(LOGIN_REMEMBER_ME_EXP)
                .setPublicUrl(PUBLIC_URL)
                .setPublicGetUrl(PUBLIC_GET_URL)
                .setJwtSecretKey(jwtSecretKey)
        );
    }
}
