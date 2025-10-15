package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "searches", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔗 L’utilisateur qui effectue la recherche */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "L'utilisateur est obligatoire.")
    private User user;

    /** 🔍 Les critères de recherche (stockés en JSON dans PostgreSQL) */
    @NotNull(message = "Les critères de recherche sont obligatoires.")
    @Column(columnDefinition = "jsonb", nullable = false)
    private String criteria;

    /** 💾 Indique si la recherche a été sauvegardée par l’utilisateur */
    @Column(nullable = false)
    private boolean saved = false;

    /** 🕒 Date et heure d’enregistrement de la recherche */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** Initialise la date automatiquement */
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", saved=" + saved +
                ", createdAt=" + createdAt +
                '}';
    }
}
