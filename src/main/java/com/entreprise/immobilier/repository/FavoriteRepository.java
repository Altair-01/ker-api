package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 FAVORITES
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
}
