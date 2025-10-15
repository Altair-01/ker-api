package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.auth.AuthResponse;
import com.entreprise.immobilier.dto.auth.LoginRequest;
import com.entreprise.immobilier.dto.auth.RegisterRequest;
import com.entreprise.immobilier.service.impl.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    /** 🔐 Enregistrement d’un nouvel utilisateur */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /** 🔑 Connexion utilisateur */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
