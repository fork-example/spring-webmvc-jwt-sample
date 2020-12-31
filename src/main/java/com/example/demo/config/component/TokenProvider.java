package com.example.demo.config.component;

import com.example.demo.config.SecretProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final SecretProperties secretProperties;

    private final UserDetailsService userDetailsService;

    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretProperties.getSecretKey().getBytes());
    }

    public String createToken(String username) {

        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + secretProperties.getValidityInMs());

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }


    public String getUsername(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        }  catch (JwtException | IllegalArgumentException e) {
            throw new NonceExpiredException("token invalid or expired");
//            throw new InvalidAuthenticationException("token invalid or expired");

        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new NonceExpiredException("token invalid or expired");
//            throw new InvalidAuthenticationException("token invalid or expired");
        }
    }

}
