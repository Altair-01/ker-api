package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 PROPERTIES
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAgencyId(Long agencyId);
    List<Property> findByStatus(String status);
    List<Property> findByPriceBetween(Double min, Double max);
}
