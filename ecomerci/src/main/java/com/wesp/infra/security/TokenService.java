package com.wesp.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wesp.infra.exception.JwtTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    @Value("${api.secutiry.token.secret}")
    private String secret;

    @Value("${api.secutiry.token.expiration}")
    private Long expiration;

    @Value("${api.secutiry.token.header}")
    private String headerString;

    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles= populateAuthorities(authorities);
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(headerString)
                    .withSubject(authentication.getName())
                    .withClaim("email", authentication.getName())
                    .withClaim("authorities", roles)
                    .withExpiresAt(getExpiration())
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            throw new JwtTokenException("Parâmetros inválidos ao criar o token: " + e.getMessage(), e);
        } catch (JWTCreationException e) {
            throw new JwtTokenException("Erro ao criar token JWT: " + e.getMessage(), e);
        }

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
    Set<String> authoritiesSet = new HashSet<>();
    authorities.forEach(authority -> authoritiesSet.add(authority.getAuthority()));
    return String.join(",", authoritiesSet);

    }

    private Instant getExpiration() {
        return Instant.now().plusMillis(expiration);
    }


    public String getSubject(String tokenJwt) {
        if (tokenJwt.startsWith("Bearer ")) {
            tokenJwt = tokenJwt.substring(7); // Remove "Bearer "
        }
            try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(headerString)
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

        } catch (JWTVerificationException e){
            throw new JwtTokenException("Error to verify token" + e.getMessage(), e);
        }
    }

    public Object getAuthorities(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(headerString)
                    .build()
                    .verify(token)
                    .getClaim("authorities")
                    .asString();
        } catch (JWTVerificationException e){
            throw new JwtTokenException("Error to verify token" + e.getMessage(), e);
        }
    }

}