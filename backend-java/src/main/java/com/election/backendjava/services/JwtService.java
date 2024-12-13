package com.election.backendjava.services;

import com.election.backendjava.APIconfig;
import com.election.backendjava.dto.JWToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    public static final String JWT_ATTRIBUTE_NAME = "jwTokenInfo";

    private final APIconfig apiConfig;

    @Autowired
    public JwtService(APIconfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    private final String JWT_ROLE_CLAIM = "role";
    public String encode(String username, Long userId, String role) {
        return Jwts.builder()
                .setSubject(username)
                .setId(userId.toString())
                .setIssuer(apiConfig.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + apiConfig.getTokenDurationOfValidity() * 1000L))
                .claim(JWT_ROLE_CLAIM, role)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public JWToken decode(String token) {
        Claims claims = getClaimsFromToken(token);
        if (!claims.getIssuer().equals(apiConfig.getIssuer())) {
            throw new MalformedJwtException("Invalid issuer");
        }
        return new JWToken(
                claims.getSubject(),
                Long.valueOf(claims.getId()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(apiConfig.getPassphrase()));
    }
}
