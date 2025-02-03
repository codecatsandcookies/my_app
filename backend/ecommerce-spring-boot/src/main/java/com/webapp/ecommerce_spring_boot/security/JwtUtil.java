package com.webapp.ecommerce_spring_boot.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

//    private static SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static SecretKey SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
        SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
    }

//    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
//        byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
//        SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
//    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, String userEmail) {
        try {
            String extractedEmail = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return extractedEmail.equals(userEmail);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
