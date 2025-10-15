package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageDTO {

    @NotNull(message = "L'expéditeur est obligatoire.")
    private Long senderId;

    @NotNull(message = "Le destinataire est obligatoire.")
    private Long receiverId;

    @NotBlank(message = "Le contenu du message ne peut pas être vide.")
    private String message;
}
