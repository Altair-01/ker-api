package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @NotBlank(message = "L'action est obligatoire.")
    private String action;

    private String details; // JSON en texte libre
}
