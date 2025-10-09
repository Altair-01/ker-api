package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 CONTRACTS
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByClientId(Long clientId);
    List<Contract> findByAgentId(Long agentId);
}
