package com.cscb869.carserviceserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cscb869.carserviceserver.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${carserviceserver.jwt.secret}")
    private String secret;
    @Value("${app.jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication, String role, Long id) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expiration);
        String token = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("CSA")
                    .withSubject(email)
                    .withClaim("id", id)
                    .withClaim("role",role)
                    .withIssuedAt(currentDate)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch(JWTCreationException exception){
            // Failed to create JWT token

        }

        return token;
    }

    public String getAccountFromJWT(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getSubject();
        }catch (JWTDecodeException exception){
            throw new InvalidTokenException(HttpStatus.BAD_REQUEST,"Invalid token");
        }
    }

    public boolean isTokenValid(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("CSA")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;

        }catch (JWTVerificationException exception) {
            throw new InvalidTokenException(HttpStatus.BAD_REQUEST,"Invalid token");
        }

    }

}
