package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Favorite;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    List<Favorite> findByProperty(Property property);
    boolean existsByUserAndProperty(User user, Property property);
}
