package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 🔹 AGENCIES
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    Optional<Agency> findByName(String name);
}
