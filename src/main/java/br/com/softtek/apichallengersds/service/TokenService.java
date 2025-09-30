package br.com.softtek.apichallengersds.service;

import br.com.softtek.apichallengersds.model.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserAuth user) {
        try {
            return Jwts.builder()
                    .setIssuer("api-challenger-sds")
                    .setSubject(user.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(generateExpirationDate())
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    private Date generateExpirationDate() {
        Instant expirationInstant = LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        return Date.from(expirationInstant);
    }
}