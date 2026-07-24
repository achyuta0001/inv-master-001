package com.inv.invmaster001.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Injected from the required jwt.secret property (JWT_SECRET env var). The
    // Spring-managed bean fails fast if it is unset — it never falls back to
    // the field initializer below, which exists ONLY as a deterministic key for
    // unit tests that construct JwtService directly via `new JwtService()`.
    @Value("${jwt.secret}")
    private String SECRET =
            "dGVzdC1vbmx5LXNlY3JldC1ub3QtdXNlZC1pbi1wcm9kdWN0aW9uLTIwMjY=";

    private final long accessExpiration = 1000 * 60 * 15; // 15 min
    private final long refreshExpiration = 1000L * 60 * 60 * 24 * 7; // 7 days

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(SECRET)
        );
    }

    public String generateAccessToken(UserDetails user) {
        return generateToken(user, accessExpiration);
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(user, refreshExpiration);
    }

    private String generateToken(UserDetails user, long expiry) {

        String role = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiry)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {

        return extractClaims(token).getSubject();
    }

    public boolean isValid(String token) {

        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}