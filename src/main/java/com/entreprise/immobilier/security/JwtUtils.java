package com.entreprise.immobilier.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * 🔐 Utilitaire JWT : génération, validation et extraction d’informations.
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /** Clé secrète longue pour HS256 — à stocker dans les variables d’environnement en production */
    private static final String JWT_SECRET = "SuperSecretKeyForRealEstateApplication123456789!@#2025";
    /** Durée de validité : 1 jour (en ms) */
    private static final long JWT_EXPIRATION_MS = 86400000;

    /** ✅ Génère la clé signée */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /** ✅ Génère un JWT avec le username */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** ✅ Extrait le username depuis le token */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** ✅ Extrait une claim spécifique */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseClaims(token);
        return claimsResolver.apply(claims);
    }

    /** ✅ Parse le token et renvoie les claims */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** ✅ Validation complète avec gestion d’erreurs détaillée */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;

        } catch (ExpiredJwtException e) {
            logger.error("❌ Token expiré : {}", e.getMessage());
            throw new JwtValidationException("Token expiré. Veuillez vous reconnecter.");

        } catch (UnsupportedJwtException e) {
            logger.error("❌ Token non supporté : {}", e.getMessage());
            throw new JwtValidationException("Type de token non supporté.");

        } catch (MalformedJwtException e) {
            logger.error("❌ Token mal formé : {}", e.getMessage());
            throw new JwtValidationException("Token mal formé.");

        } catch (SignatureException e) {
            logger.error("❌ Signature du token invalide : {}", e.getMessage());
            throw new JwtValidationException("Signature du token invalide.");

        } catch (IllegalArgumentException e) {
            logger.error("❌ Token vide ou illisible : {}", e.getMessage());
            throw new JwtValidationException("Token vide ou illisible.");

        } catch (JwtException e) {
            logger.error("❌ Erreur JWT inconnue : {}", e.getMessage());
            throw new JwtValidationException("Erreur inconnue lors de la validation du token.");
        }
    }
}
