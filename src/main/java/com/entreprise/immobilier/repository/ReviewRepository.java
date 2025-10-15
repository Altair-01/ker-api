package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.Review;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProperty(Property property);
    List<Review> findByUser(User user);
}
