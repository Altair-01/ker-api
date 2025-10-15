package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByAgent(Agent agent);
    List<Property> findByCity(String city);
}
