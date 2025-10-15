package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.AppointmentDTO;
import com.entreprise.immobilier.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    Appointment createAppointment(AppointmentDTO appointmentDTO);

    Appointment updateAppointment(Long id, AppointmentDTO appointmentDTO);

    void deleteAppointment(Long id);
}
