import io.jsonwebtoken.*;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWToken {

    private static final String JWT_ROLE_CLAIM = "role";
    private static final String JWT_IPADDRESS_CLAIM = "ipa";
    private static final String JWT_ISSUER_CLAIM = "iss";
    private static final String JWT_CALLNAME_CLAIM = "sub";
    private static final String JWT_ACCOUNTID_CLAIM = "id";

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
                .subject(this.callName)
                .id(this.accountId.toString())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .claim(JWT_ROLE_CLAIM, this.role)
                .claim(JWT_IPADDRESS_CLAIM, this.ipAddress != null ? this.ipAddress : "1.1.1.1")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public static JWToken decode(String token, String issuer, String passphrase)
            throws ExpiredJwtException, MalformedJwtException {
        // Validate the token string and extract the claims
        Key key = getKey(passphrase);
        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
        Claims claims = jws.getBody();
        // check the issuer claim
        if (!claims.getIssuer().equals(issuer)) {
            throw new MalformedJwtException("Invalid issuer");
        }
        // build our token from the extracted claims
        JWToken jwToken = new JWToken(
                claims.getSubject(),
                Long.valueOf(claims.getId()),
                claims.get(JWT_ROLE_CLAIM).toString()
        );

        jwToken.setIpAddress((String) claims.get(JWT_IPADDRESS_CLAIM));
        return jwToken;
    }

    private static Key getKey(String passphrase) {
        byte[] hmacKey = passphrase.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(hmacKey, SignatureAlgorithm.HS512.getJcaName());
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}