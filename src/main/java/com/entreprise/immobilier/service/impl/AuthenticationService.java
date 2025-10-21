package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.auth.AuthResponse;
import com.entreprise.immobilier.dto.auth.LoginRequest;
import com.entreprise.immobilier.dto.auth.RegisterRequest;
import com.entreprise.immobilier.model.RefreshToken;
import com.entreprise.immobilier.model.Role;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.security.JwtUtils;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 🧩 Service d’authentification et d’enregistrement des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    /**
     * 🔐 Enregistrement d’un nouvel utilisateur
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException("Un utilisateur avec cet email existe déjà.");
        }

        // 🧩 Détermination automatique du rôle
        Role userRole;
        if (request.getRole() == null || request.getRole().isBlank()) {
            userRole = Role.CLIENT; // rôle par défaut
        } else {
            try {
                userRole = Role.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                userRole = Role.CLIENT; // fallback si le rôle est invalide
            }
        }

        // 🔧 Création de l’utilisateur
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(userRole)
                .enabled(true)
                .build();

        userRepository.save(user);

        // 🔑 Génération du JWT + refresh token
        String accessToken = jwtUtils.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /**
     * 🔑 Connexion d’un utilisateur existant
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé."));

        // ✅ Vérifie les identifiants
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 🔑 Génère un nouveau JWT + refresh token
        String accessToken = jwtUtils.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken.getToken())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
