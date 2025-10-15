package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PropertyDTO {

    @NotBlank(message = "Le titre est obligatoire.")
    private String title;

    private String description;

    @Min(value = 1, message = "Le prix doit être supérieur à 0.")
    private double price;

    @Min(value = 1, message = "La taille doit être positive.")
    private int size;

    @NotBlank(message = "L'adresse est obligatoire.")
    private String address;

    @NotBlank(message = "La ville est obligatoire.")
    private String city;

    @NotBlank(message = "Le code postal est obligatoire.")
    private String postalCode;

    @Pattern(regexp = "^(appartement|maison|terrain)$", message = "Type invalide.")
    private String type;

    @Pattern(regexp = "^(à vendre|à louer|vendu|loué)$", message = "Statut invalide.")
    private String status;

    private Long agentId;
}
