package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.AuthTokenDTO;
import com.entreprise.immobilier.model.AuthToken;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.AuthTokenRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.AuthTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;

    @Override
    public List<AuthToken> getAllTokens() {
        return authTokenRepository.findAll();
    }

    @Override
    public Optional<AuthToken> getTokenById(Long id) {
        return authTokenRepository.findById(id);
    }

    @Override
    public Optional<AuthToken> getTokenByValue(String token) {
        return authTokenRepository.findByToken(token);
    }

    @Override
    public AuthToken createToken(AuthTokenDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        AuthToken authToken = AuthToken.builder()
                .user(user)
                .token(dto.getToken())
                .expiryDate(dto.getExpiryDate())
                .active(dto.isActive())
                .createdAt(LocalDateTime.now())
                .build();

        return authTokenRepository.save(authToken);
    }

    @Override
    public AuthToken updateToken(Long id, AuthTokenDTO dto) {
        AuthToken existing = authTokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Token introuvable."));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        existing.setUser(user);
        existing.setToken(dto.getToken());
        existing.setExpiryDate(dto.getExpiryDate());
        existing.setActive(dto.isActive());

        return authTokenRepository.save(existing);
    }

    @Override
    public void deactivateToken(Long id) {
        AuthToken token = authTokenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Token introuvable."));
        token.setActive(false);
        authTokenRepository.save(token);
    }

    @Override
    public void deleteToken(Long id) {
        if (!authTokenRepository.existsById(id)) {
            throw new EntityNotFoundException("Token introuvable.");
        }
        authTokenRepository.deleteById(id);
    }

    @Override
    public List<AuthToken> getTokensByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return authTokenRepository.findByUser(user);
    }
}
