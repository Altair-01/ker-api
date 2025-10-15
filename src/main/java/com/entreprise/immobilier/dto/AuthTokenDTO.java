package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthTokenDTO {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire.")
    private Long userId;

    @NotBlank(message = "Le token est obligatoire.")
    private String token;

    @NotNull(message = "La date d'expiration est obligatoire.")
    @Future(message = "La date d'expiration doit être dans le futur.")
    private LocalDateTime expiryDate;

    private boolean active = true;
}
