package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Contract;
import com.entreprise.immobilier.model.Agent;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByAgent(Agent agent);
    List<Contract> findByClient(User client);
}
