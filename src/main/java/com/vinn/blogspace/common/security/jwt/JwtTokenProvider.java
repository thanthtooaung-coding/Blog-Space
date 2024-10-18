package com.vinn.blogspace.common.security.jwt;

import com.vinn.blogspace.common.security.principal.UserPrincipal;
import com.vinn.blogspace.common.utils.ApiKeys;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtTokenProvider {

    private final ApiKeys apiKeys;

    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

    public JwtTokenProvider(ApiKeys apiKeys) {
        this.apiKeys = apiKeys;
    }

    private Key getSigningKey() {
        byte[] keyBytes = apiKeys.getJwtSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + apiKeys.getJwtExpirationInMs());

        return Jwts.builder()
                .claim("sub", Long.toString(userPrincipal.getId()))
                .claim("iat", now)
                .claim("exp", expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        if (invalidatedTokens.contains(authToken)) {
            return false;
        }

        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Log error
        }
        return false;
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
}