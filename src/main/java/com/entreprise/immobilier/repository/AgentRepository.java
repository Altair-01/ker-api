package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    boolean existsByLicenseNumber(String licenseNumber);
}
