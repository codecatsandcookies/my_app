package com.webapp.ecommerce_spring_boot.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class SecretKeyProvider {

    private static final String SECRET = "3aIV82WdBBEO3qXul+/cpRtLdy1ibTkddy/O81egxQg=";  // Replace with a strong key

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}

