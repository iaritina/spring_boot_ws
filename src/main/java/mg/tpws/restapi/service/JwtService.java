package mg.tpws.restapi.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mg.tpws.restapi.model.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {
    private String SECRET =
            "ma-cle-secrete-ma-cle-secrete-ma-cle-secrete";

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 20);
        return Jwts.builder()
                .subject(user.getEmail()) // identifiant principal
                .claim("role", user.getRole().name())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token validation error : " + e.getMessage());
            return false;
        }
    }

    public String extractEmail(String token) {
        return Jwts.parser().verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

}
