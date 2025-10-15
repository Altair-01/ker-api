package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.auth.AuthResponse;
import com.entreprise.immobilier.dto.auth.LoginRequest;
import com.entreprise.immobilier.dto.auth.RegisterRequest;
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

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /** 🔐 Enregistrement d’un nouvel utilisateur */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException("Un utilisateur avec cet email existe déjà.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token, user.getRole(), user.getEmail());
    }

    /** 🔑 Connexion d’un utilisateur existant */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token, user.getRole(), user.getEmail());
    }
}
