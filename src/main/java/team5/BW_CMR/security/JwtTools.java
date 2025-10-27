package team5.BW_CMR.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team5.BW_CMR.entities.Utente;
import team5.BW_CMR.exceptions.UnauthorizedException;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken (Utente utente) {
        return Jwts.builder().issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(utente.getId())).signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }

    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        } catch (Exception ex) {
            throw new UnauthorizedException("Non autorizzato!");
        }
    }

    public UUID extractIdFromToken(String accessToken) {
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject());
    }
}
