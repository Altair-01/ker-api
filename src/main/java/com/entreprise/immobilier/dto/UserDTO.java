package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire.")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire.")
    @Email(message = "L'email n'est pas valide.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    private String password;

    @Pattern(regexp = "^(\\+221)?[0-9]{9,12}$", message = "Le numéro de téléphone doit être valide (ex: +221771234567).")
    private String phoneNumber;

    @NotBlank(message = "Le rôle est obligatoire.")
    @Pattern(regexp = "^(client|agent|administrator)$", message = "Le rôle doit être 'client', 'agent' ou 'administrateur'.")
    private String role;

}
