package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    boolean existsByName(String name);
}
