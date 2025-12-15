package com.santa.api_gateway.config.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private String secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretKey){
        this.secretKey = secretKey;
    }

    public void validateToken(final String token) {
        Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token);
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}