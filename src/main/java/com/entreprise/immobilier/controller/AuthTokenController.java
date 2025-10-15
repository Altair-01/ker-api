package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.AuthTokenDTO;
import com.entreprise.immobilier.model.AuthToken;
import com.entreprise.immobilier.service.interfaces.AuthTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class AuthTokenController {

    private final AuthTokenService authTokenService;

    @GetMapping
    public List<AuthToken> getAllTokens() {
        return authTokenService.getAllTokens();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthToken> getTokenById(@PathVariable Long id) {
        return authTokenService.getTokenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/value/{token}")
    public ResponseEntity<AuthToken> getTokenByValue(@PathVariable String token) {
        return authTokenService.getTokenByValue(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuthToken>> getTokensByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(authTokenService.getTokensByUser(userId));
    }

    @PostMapping
    public ResponseEntity<AuthToken> createToken(@Valid @RequestBody AuthTokenDTO dto) {
        return ResponseEntity.ok(authTokenService.createToken(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthToken> updateToken(@PathVariable Long id, @Valid @RequestBody AuthTokenDTO dto) {
        return ResponseEntity.ok(authTokenService.updateToken(id, dto));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateToken(@PathVariable Long id) {
        authTokenService.deactivateToken(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable Long id) {
        authTokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }
}
