package com.entreprise.immobilier.dto;

import com.entreprise.immobilier.model.Agent;
import com.entreprise.immobilier.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Le prénom est obligatoire.")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire.")
    private String lastName;

    @Email(message = "L'adresse email doit être valide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String password;

    @Pattern(
            regexp = "^(\\+221)?[0-9]{9,12}$",
            message = "Le numéro de téléphone doit être valide (ex: +221770112233)"
    )
    private String phoneNumber;

    @NotBlank(message = "Le rôle est obligatoire.")
    @Pattern(
            regexp = "^(client|agent|administrateur)$",
            message = "Le rôle doit être 'client', 'agent' ou 'administrateur'."
    )
    private String role;

    private boolean enabled = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
