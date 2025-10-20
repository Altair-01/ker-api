package com.entreprise.immobilier.security;

/** Exception personnalisée pour les erreurs liées aux JWT */
public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {
        super(message);
    }
}
