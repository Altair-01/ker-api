package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 AGENTS
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    List<Agent> findByAgencyId(Long agencyId);
}
