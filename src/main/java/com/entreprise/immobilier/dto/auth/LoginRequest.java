package com.entreprise.immobilier.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "L'adresse email est invalide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;
}
