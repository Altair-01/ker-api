package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.AuthToken;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByToken(String token);
    List<AuthToken> findByUser(User user);
    List<AuthToken> findByUserAndActiveTrue(User user);
}
