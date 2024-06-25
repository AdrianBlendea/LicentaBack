package com.example.programingappapi.jwtutil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenProvider {

    private static final String SECRET_KEY = "7ZOkGijejRONJNeo8jQRJJrrRrIULBXpOfbXvkx4aYrfLczomfyWH8LGcvaPdsX4SG6saCWuZbhYEvovqoErQA==";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public static String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String rolesString = roles.stream().collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", rolesString)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
