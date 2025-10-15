package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDTO {

    @NotNull(message = "L'identifiant du bien immobilier est obligatoire.")
    private Long propertyId;

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @Min(value = 1, message = "La note minimale est 1.")
    @Max(value = 5, message = "La note maximale est 5.")
    private int rating;

    private String comment;
}
