package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.RefreshToken;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /** 🔍 Recherche un refresh token par sa valeur */
    Optional<RefreshToken> findByToken(String token);

    /** 🗑️ Supprime les tokens associés à un utilisateur spécifique */
    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
    void deleteByUser(User user);

    /** 🔍 Récupère tous les tokens expirés (plus performante que le stream côté service) */
    @Query("SELECT r FROM RefreshToken r WHERE r.expiryDate < :now")
    List<RefreshToken> findAllByExpiryDateBefore(Instant now);

    /** ⚙️ Force la synchronisation immédiate avec la base */
    @Override
    void flush();
}
