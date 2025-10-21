package com.entreprise.immobilier.dto;

import com.entreprise.immobilier.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 🎯 Représentation simplifiée de l’utilisateur pour les échanges API.
 */
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

    /** ⚠️ Le mot de passe est uniquement utilisé à la création */
    private String password;

    @Pattern(
            regexp = "^(\\+221)?[0-9]{9,12}$",
            message = "Le numéro de téléphone doit être valide (ex: +221770112233)"
    )
    private String phoneNumber;

    /** 🎭 Rôle de l’utilisateur (ADMIN, AGENT, CLIENT) */
    private Role role; // 🔥 type enum, cohérent avec ton modèle User

    private boolean enabled = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
