package com.entreprise.immobilier.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ✅ Réponse standard après login ou enregistrement :
 * contient le JWT et le refresh token
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;          // Access Token (JWT)
    private String refreshToken;   // Refresh Token
    private String email;          // Email de l’utilisateur
    private String role;           // Rôle de l’utilisateur
}
