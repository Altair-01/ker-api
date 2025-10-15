package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @NotBlank(message = "Le message de la notification est obligatoire.")
    private String message;

    private boolean isRead = false;
}
