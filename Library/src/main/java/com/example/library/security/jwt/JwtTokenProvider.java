package com.example.library.security.jwt;


import com.example.library.security.SecurityConstant;
import com.example.library.service.UserDetail;
import com.example.library.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String KEY_AUTHORITIES = "authorities";
    private static final String SEPARATOR = " ";
    private SecretKey key;

    private final UserDetailService userDetailService;

    public JwtTokenProvider(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    private SecretKey getKey() {
        if (key == null) {
            // Decode the secret key from base64 and create a SecretKey instance
            byte[] keyBytes = Base64.getDecoder().decode(SecurityConstant.getInstance().getJwtSecretKey());
            this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
        }
        return key;
    }

    public String generateToken(@NotNull String username, long expirationMs) {
        UserDetail userDetail = (UserDetail) userDetailService.loadUserByUsername(username);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setId(String.valueOf(userDetail.getUser().getId()))
                .setSubject(userDetail.getUsername())
                .claim(KEY_AUTHORITIES, getAuthorities(userDetail))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
        UserDetails userDetail = User.builder()
                .username(claims.getSubject())
                .password("")
                .authorities(claims.get(KEY_AUTHORITIES, String.class).split(SEPARATOR))
                .build();
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }

    public String getUserName(String token) {
        try {
            return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception ex) {
            return "";
        }
    }

    public String resolveToken(@NotNull HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @NotNull
    private String getAuthorities(@NotNull UserDetails userDetail) {
        return userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(SEPARATOR));
    }
}
