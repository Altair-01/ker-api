package com.entreprise.immobilier.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire.")
    private String lastName;

    @Email(message = "L'adresse email est invalide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;

    private String phoneNumber;
    private String role = "client"; // par défaut client
}
