package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 APPOINTMENTS
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAgentId(Long agentId);
    List<Appointment> findByClientId(Long clientId);
}
