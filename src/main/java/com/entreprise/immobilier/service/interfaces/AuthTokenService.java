package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.AuthTokenDTO;
import com.entreprise.immobilier.model.AuthToken;

import java.util.List;
import java.util.Optional;

public interface AuthTokenService {

    List<AuthToken> getAllTokens();

    Optional<AuthToken> getTokenById(Long id);

    Optional<AuthToken> getTokenByValue(String token);

    AuthToken createToken(AuthTokenDTO dto);

    AuthToken updateToken(Long id, AuthTokenDTO dto);

    void deactivateToken(Long id);

    void deleteToken(Long id);

    List<AuthToken> getTokensByUser(Long userId);
}
