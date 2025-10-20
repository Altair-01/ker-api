package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.UserDTO;
import com.entreprise.immobilier.dto.auth.AuthResponse;
import com.entreprise.immobilier.dto.auth.LoginRequest;
import com.entreprise.immobilier.dto.auth.RegisterRequest;
import com.entreprise.immobilier.dto.auth.TokenRefreshRequest;
import com.entreprise.immobilier.model.RefreshToken;
import com.entreprise.immobilier.security.JwtUtils;
import com.entreprise.immobilier.service.impl.AuthenticationService;
import com.entreprise.immobilier.service.impl.RefreshTokenService;
import com.entreprise.immobilier.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    /** 🔐 Enregistrement d’un nouvel utilisateur */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /** 🔑 Connexion utilisateur */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /** 👤 Récupération de l’utilisateur connecté */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Non authentifié",
                    "message", "Aucun utilisateur connecté ou token invalide."
            ));
        }

        String email = authentication.getName();
        return userService.findByEmail(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body(Map.of(
                        "error", "Utilisateur introuvable",
                        "message", "Aucun utilisateur correspondant au token."
                )));
    }

    /** 🔄 Rafraîchir le token JWT */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newToken = jwtUtils.generateToken(user.getEmail());
                    return ResponseEntity.ok(Map.of(
                            "accessToken", newToken,
                            "refreshToken", refreshToken
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(403).body(Map.of(
                        "error", "Refresh token invalide",
                        "message", "Le token fourni n’existe pas ou a expiré."
                )));
    }

    /** 🚪 Déconnexion sécurisée : supprime le refresh token */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Non authentifié",
                    "message", "Impossible de se déconnecter : aucun utilisateur connecté."
            ));
        }

        String email = authentication.getName();
        return userService.findByEmail(email)
                .map(user -> {
                    refreshTokenService.deleteByUserId(user.getId());
                    return ResponseEntity.ok(Map.of(
                            "message", "Déconnexion réussie.",
                            "status", "OK"
                    ));
                })
                .orElse(ResponseEntity.status(404).body(Map.of(
                        "error", "Utilisateur introuvable",
                        "message", "Impossible de supprimer le refresh token."
                )));
    }
}
