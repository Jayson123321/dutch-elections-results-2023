package com.election.backendjava.utils;

import io.jsonwebtoken.*;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class JWToken {

    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";
    private String callName;
    private Long accountId;
    private String role;
    private String ipAddress;

    public JWToken(String callName, Long accountId, String role) {
        this.callName = callName;
        this.accountId = accountId;
        this.role = role;
    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .setSubject(this.callName)
                .setId(this.accountId.toString())
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }
}