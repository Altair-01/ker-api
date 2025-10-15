package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatisticDTO {

    @NotBlank(message = "Le type de statistique est obligatoire.")
    private String type;

    @NotNull(message = "La valeur est obligatoire.")
    private Double value;

    private LocalDateTime date = LocalDateTime.now();
}
