package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.AppointmentDTO;
import com.entreprise.immobilier.model.Agent;
import com.entreprise.immobilier.model.Appointment;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.AgentRepository;
import com.entreprise.immobilier.repository.AppointmentRepository;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment createAppointment(AppointmentDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien introuvable."));
        User client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));
        Agent agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));

        Appointment appointment = Appointment.builder()
                .property(property)
                .client(client)
                .agent(agent)
                .date(dto.getDate())
                .status(dto.getStatus() != null ? dto.getStatus() : "waiting")
                .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Long id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rendez-vous introuvable."));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien introuvable."));
        User client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));
        Agent agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));

        existing.setProperty(property);
        existing.setClient(client);
        existing.setAgent(agent);
        existing.setDate(dto.getDate());
        existing.setStatus(dto.getStatus());

        return appointmentRepository.save(existing);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Rendez-vous introuvable.");
        }
        appointmentRepository.deleteById(id);
    }
}
