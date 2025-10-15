package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔗 Contrat lié au paiement */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    /** 💸 Montant payé */
    @Min(value = 1, message = "Le montant doit être supérieur à zéro.")
    @Column(nullable = false)
    private double amount;

    /** 🕒 Date du paiement */
    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    /** ✅ Statut du paiement */
    @NotNull(message = "Le statut du paiement est obligatoire.")
    @Column(nullable = false, length = 50)
    private String status; // "réussi", "échoué", "en attente"

    /** 💳 Mode de paiement */
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @PrePersist
    public void prePersist() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", contractId=" + (contract != null ? contract.getId() : null) +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
