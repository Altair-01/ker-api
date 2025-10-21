package com.entreprise.immobilier.dto;

import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDTO {

    private Long id;

    @NotBlank(message = "Le titre est obligatoire.")
    private String title;

    private String description;

    @Min(value = 1, message = "Le prix doit être supérieur à 0.")
    private double price;

    @Min(value = 1, message = "La taille doit être positive.")
    private double size;

    @NotBlank(message = "L'adresse est obligatoire.")
    private String address;

    @NotBlank(message = "La ville est obligatoire.")
    private String city;

    private String postalCode;

    @NotNull(message = "Le type est obligatoire.")
    private String type;

    @NotNull(message = "Le statut est obligatoire.")
    private String status;

    private String createdAt;
    private String updatedAt;
    private Long agentId;
}
