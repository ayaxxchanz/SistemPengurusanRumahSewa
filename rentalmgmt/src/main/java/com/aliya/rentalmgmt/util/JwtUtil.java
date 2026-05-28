package com.aliya.rentalmgmt.util;


import com.aliya.rentalmgmt.constant.ApplicationConstant;
import com.aliya.rentalmgmt.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final Environment env;

    public String generateJwtToken(Authentication authentication) {
        String jwt = "";
        String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                ApplicationConstant.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        User fetchedUser = (User) authentication.getPrincipal();
        // generate header, payload & digital signature
        jwt = Jwts.builder().issuer("Rental Mgmt").subject("JWT Token")
                .claim("phone",  fetchedUser.getPhone()) // claim(key, value)
                .claim("email",  fetchedUser.getEmail()) // claim(key, value)
                .claim("roles", authentication.getAuthorities().stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1 hour expiration
                .signWith(secretKey).compact();

        return jwt;
    }
}
