package com.example.library.security;

public class SecurityConstantBuilder {

    String loginPage;
    String logoutPage;
    String loginFailurePage;
    String loginSuccessPage;
    long loginSessionExp;
    long loginRememberMeExp;
    String[] publicUrl;
    String[] publicGetUrl;
    String jwtSecretKey;

    public SecurityConstantBuilder setLoginPage(String loginPage) {
        this.loginPage = loginPage;
        return this;
    }

    public SecurityConstantBuilder setLogoutPage(String logoutPage) {
        this.logoutPage = logoutPage;
        return this;
    }

    public SecurityConstantBuilder setLoginFailurePage(String loginFailurePage) {
        this.loginFailurePage = loginFailurePage;
        return this;
    }

    public SecurityConstantBuilder setLoginSuccessPage(String loginSuccessPage) {
        this.loginSuccessPage = loginSuccessPage;
        return this;
    }

    public SecurityConstantBuilder setLoginSessionExp(long loginSessionExp) {
        this.loginSessionExp = loginSessionExp;
        return this;
    }

    public SecurityConstantBuilder setLoginRememberMeExp(long loginRememberMeExp) {
        this.loginRememberMeExp = loginRememberMeExp;
        return this;
    }

    public SecurityConstantBuilder setPublicUrl(String[] publicUrl) {
        this.publicUrl = publicUrl;
        return this;
    }

    public SecurityConstantBuilder setPublicGetUrl(String[] publicGetUrl) {
        this.publicGetUrl = publicGetUrl;
        return this;
    }

    public SecurityConstantBuilder setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
        return this;
    }
}
