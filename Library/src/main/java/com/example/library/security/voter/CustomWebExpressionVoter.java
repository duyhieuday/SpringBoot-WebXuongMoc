package com.example.library.security.voter;


import com.example.library.service.UserDetailService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public class CustomWebExpressionVoter extends WebExpressionVoter {

    private static final boolean debug = true;
    private static final Log logger = LogFactory.getLog(CustomWebExpressionVoter.class);

    private final UserDetailService userDetailService;

    public CustomWebExpressionVoter(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public int vote(@NotNull Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {

        int result = super.vote(authentication, filterInvocation, attributes);
        if (result != ACCESS_DENIED) {
            return result;
        }

        String url = validateUrl(filterInvocation.getRequestUrl());
        Collection<? extends GrantedAuthority> authorities = getAuthorities(authentication);

        log("--- Voter url:  " + url);
        log("--- Authorities " + authorities);

        for (GrantedAuthority authority : authorities) {
            PermissionMatcher matcher = new PermissionMatcher(authority.getAuthority());
            if (matcher.matches(url)) {
                log("--> Granted");
                return ACCESS_GRANTED;
            }
        }
        log("--> Denied");
        return result;
    }

    @NotNull
    private String validateUrl(@NotNull String url) {
        if (url.endsWith("/") || url.endsWith("?")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(@NotNull Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities;

        if (authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            authorities = userDetails.getAuthorities();
        } else {
            authorities = authentication.getAuthorities();
        }

        return authorities;
    }


    private static void log(Object object) {
        if (debug) logger.info(object);
    }

}
