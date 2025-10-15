package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contracts", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔗 Bien immobilier concerné */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /** 🔗 Client concerné (acheteur ou locataire) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    /** 🔗 Agent responsable de la transaction */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    /** 📑 Type de contrat : location ou vente */
    @NotNull(message = "Le type de contrat est obligatoire.")
    @Column(length = 50, nullable = false)
    private String type;

    /** 📆 Date de début du contrat */
    @NotNull(message = "La date de début est obligatoire.")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** 📆 Date de fin (facultative pour les ventes) */
    @Column(name = "end_date")
    private LocalDate endDate;

    /** 💰 Montant du contrat */
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à zéro.")
    @Column(nullable = false)
    private Double price;

    /** 📜 Statut : signé, en attente, annulé */
    @Column(length = 50, nullable = false)
    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null) status = "en attente";
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", startDate=" + startDate +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
