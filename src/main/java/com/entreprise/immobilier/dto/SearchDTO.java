package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @NotNull(message = "Les critères de recherche sont obligatoires.")
    private String criteriaJson; // Stocké sous forme de JSON (ex: {"ville":"Dakar","type":"maison"})

    private boolean saved = false;
}
