package com.entreprise.immobilier.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentDTO {

    @NotNull(message = "L'identifiant du contrat est obligatoire.")
    private Long contractId;

    @Min(value = 1, message = "Le montant doit être supérieur à zéro.")
    private double amount;

    @NotNull(message = "Le statut du paiement est obligatoire.")
    private String status; // "réussi", "échoué", "en attente"

    private String paymentMethod; // "virement", "carte", "espèces"
}
