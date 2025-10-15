package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractDTO {

    @NotNull(message = "Le bien immobilier est obligatoire.")
    private Long propertyId;

    @NotNull(message = "Le client est obligatoire.")
    private Long clientId;

    @NotNull(message = "L'agent est obligatoire.")
    private Long agentId;

    @NotNull(message = "Le type de contrat est obligatoire.")
    private String type; // "location" ou "vente"

    @NotNull(message = "La date de début est obligatoire.")
    private LocalDate startDate;

    private LocalDate endDate;

    @Min(value = 1, message = "Le prix doit être supérieur à zéro.")
    private double price;

    @NotNull(message = "Le statut du contrat est obligatoire.")
    private String status; // "signé", "en attente", "annulé"
}
