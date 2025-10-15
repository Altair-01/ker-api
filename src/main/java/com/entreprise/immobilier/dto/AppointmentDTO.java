package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    @NotNull(message = "L'identifiant du bien est obligatoire.")
    private Long propertyId;

    @NotNull(message = "L'identifiant du client est obligatoire.")
    private Long clientId;

    @NotNull(message = "L'identifiant de l'agent est obligatoire.")
    private Long agentId;

    @NotNull(message = "La date du rendez-vous est obligatoire.")
    @Future(message = "La date du rendez-vous doit être dans le futur.")
    private LocalDateTime date;

    private String status; // waiting, confirmed, cancelled
}
