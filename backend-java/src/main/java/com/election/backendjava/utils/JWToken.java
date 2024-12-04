package com.election.backendjava.utils;

import io.jsonwebtoken.*;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

public class JWToken {

    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_EMAIL_CLAIM = "email";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";

    private String username;
    private Long id;
    private String role;
    private String email;
    private String ipAddress;

    public JWToken(String username, Long id, String role, String email) {
        this.username = username;
        this.id = id;
        this.role = role;
        this.email = email;
    }

    public String encode(String issuer, String passphrase, int expiration) {
        Key key = getKey(passphrase);

        return Jwts.builder()
                .subject(this.username)
                .id(this.id.toString())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_EMAIL_CLAIM, this.email)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }
}