package com.sherlin.ecommercehub.service;

import com.sherlin.ecommercehub.entity.User;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;


@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationMinutes;

    public JwtService(JwtEncoder jwtEncoder,@Value("${app.jwt.expiration-minutes}")long expirationMinutes){
        this.jwtEncoder=jwtEncoder;
        this.expirationMinutes=expirationMinutes;
    }

    public String generateToken(User user){

        Instant now=Instant.now();

        Instant expiresAt=now.plus(Duration.ofMinutes(expirationMinutes));

        JwtClaimsSet claims=JwtClaimsSet.builder().issuer("ecommercehub").subject(user.getId().toString()).issuedAt(now).expiresAt(expiresAt).build();

        JwsHeader header= JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header,claims)).getTokenValue();

    }
}
