package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettingDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    private Boolean notificationsEnabled = true;

    private String language = "fr"; // par défaut français

    private String theme = "clair"; // valeurs possibles : "clair", "sombre"
}
