package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long generatedById;

    @NotBlank(message = "Le type du rapport est obligatoire.")
    private String type; // ex: "activité", "vente", "audit"

    @NotNull(message = "Le contenu du rapport est obligatoire.")
    private String contentJson; // ex: {"ventes":10,"revenu":2500000}
}
