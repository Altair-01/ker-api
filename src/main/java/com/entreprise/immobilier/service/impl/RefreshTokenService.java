package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.model.RefreshToken;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.RefreshTokenRepository;
import com.entreprise.immobilier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    // Durée de vie : 7 jours
    private static final long REFRESH_TOKEN_DURATION_MS = 7 * 24 * 60 * 60 * 1000L;

    /**
     * ✅ Crée un refresh token unique pour un utilisateur
     */
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        try {
            // Supprime l’ancien token avant d’en créer un nouveau
            refreshTokenRepository.deleteByUser(user);
            refreshTokenRepository.flush(); // ⚡ force l'exécution immédiate du DELETE
        } catch (Exception e) {
            log.warn("⚠️ Impossible de supprimer l’ancien refresh token de l’utilisateur {} : {}", user.getEmail(), e.getMessage());
        }

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_DURATION_MS))
                .build();

        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);
        log.info("✅ Nouveau refresh token créé pour l’utilisateur {}", user.getEmail());

        return savedToken;
    }

    /**
     * ✅ Vérifie si le refresh token est encore valide
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            log.warn("⏰ Refresh token expiré supprimé pour l’utilisateur {}", token.getUser().getEmail());
            throw new RuntimeException("Le Refresh Token a expiré. Veuillez vous reconnecter.");
        }
        return token;
    }

    /**
     * ✅ Recherche un refresh token par sa valeur
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * ✅ Supprime tous les refresh tokens liés à un utilisateur (ex : logout)
     */
    @Transactional
    public void deleteByUserId(Long userId) {
        userRepository.findById(userId).ifPresent(user -> {
            try {
                refreshTokenRepository.deleteByUser(user);
                refreshTokenRepository.flush();
                log.info("🚪 Refresh token supprimé pour l’utilisateur {}", user.getEmail());
            } catch (Exception e) {
                log.error("❌ Erreur lors de la suppression du refresh token pour l’utilisateur {} : {}", user.getEmail(), e.getMessage());
            }
        });
    }
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void purgeExpiredTokens() {
        List<RefreshToken> expiredTokens = refreshTokenRepository.findAllByExpiryDateBefore(Instant.now());
        if (expiredTokens.isEmpty()) {
            log.info("✅ Aucun refresh token expiré à supprimer ce soir.");
            return;
        }

        log.info("🧹 Suppression de {} refresh tokens expirés...", expiredTokens.size());
        refreshTokenRepository.deleteAll(expiredTokens);
        log.info("✅ Nettoyage terminé : {} tokens supprimés.", expiredTokens.size());
    }


}
