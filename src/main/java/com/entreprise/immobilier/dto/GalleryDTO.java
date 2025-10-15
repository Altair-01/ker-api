package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GalleryDTO {

    @NotNull(message = "L'identifiant du bien est obligatoire.")
    private Long propertyId;

    @NotBlank(message = "L'URL de l'image est obligatoire.")
    private String imageUrl;

    private boolean isMain = false;
}
