package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔗 Lien vers le bien concerné */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /** 🔗 Lien vers le client (utilisateur) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    /** 🔗 Lien vers l’agent immobilier */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    /** 🕓 Date et heure du rendez-vous */
    @NotNull(message = "La date du rendez-vous est obligatoire.")
    @Future(message = "La date du rendez-vous doit être dans le futur.")
    private LocalDateTime date;

    /** 🔄 Statut du rendez-vous */
    @Column(length = 20, nullable = false)
    private String status;

    @PrePersist
    public void prePersist() {
        if (status == null) status = "waiting";
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
