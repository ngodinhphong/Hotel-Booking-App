package com.booking.hotel.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUltils {

    @Value("${key.token.jwt}")
    private String strKey;

    public String createToken(String data){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        return Jwts.builder().subject(data).signWith(secretKey).compact();
    }

    public String decryptToken(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        String data = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();

        return data;
    }

}
