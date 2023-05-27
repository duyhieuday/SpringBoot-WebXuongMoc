package com.example.library.security;

public class SecurityConstant {

    private static SecurityConstant instance;

    public static void init(SecurityConstantBuilder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("Builder can't null");
        }
        instance = new SecurityConstant();
        instance.loginPage = builder.loginPage;
        instance.logoutPage = builder.logoutPage;
        instance.loginFailurePage = builder.loginFailurePage;
        instance.loginSuccessPage = builder.loginSuccessPage;
        instance.loginSessionExp = builder.loginSessionExp;
        instance.loginRememberMeExp = builder.loginRememberMeExp;
        instance.publicUrl = builder.publicUrl;
        instance.publicGetUrl = builder.publicGetUrl;
        instance.jwtSecretKey = builder.jwtSecretKey;
    }

    public static SecurityConstant getInstance() {
        if (instance == null) {
            throw new NullPointerException("You need init this class before call this function.");
        }
        return instance;
    }

    private SecurityConstant() {
    }

    private String loginPage;
    private String logoutPage;
    private String loginFailurePage;
    private String loginSuccessPage;
    private long loginSessionExp;
    private long loginRememberMeExp;
    private String[] publicUrl;
    private String[] publicGetUrl;
    private String jwtSecretKey;

    public String getLoginPage() {
        return loginPage;
    }

    public String getLogoutPage() {
        return logoutPage;
    }

    public String getLoginFailurePage() {
        return loginFailurePage;
    }

    public String getLoginSuccessPage() {
        return loginSuccessPage;
    }

    public long getLoginSessionExp() {
        return loginSessionExp;
    }

    public long getLoginRememberMeExp() {
        return loginRememberMeExp;
    }

    public String[] getPublicUrl() {
        return publicUrl;
    }

    public String[] getPublicGetUrl() {
        return publicGetUrl;
    }

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
}
