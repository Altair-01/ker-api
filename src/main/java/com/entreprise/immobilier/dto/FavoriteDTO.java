package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @NotNull(message = "L'identifiant du bien est obligatoire.")
    private Long propertyId;
}
