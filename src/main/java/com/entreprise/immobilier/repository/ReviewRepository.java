package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 REVIEWS
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPropertyId(Long propertyId);
    List<Review> findByUserId(Long userId);
}
