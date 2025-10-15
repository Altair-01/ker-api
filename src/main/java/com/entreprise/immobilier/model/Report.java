package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🧾 Type du rapport : "activité", "vente", "audit", etc. */
    @NotBlank(message = "Le type du rapport est obligatoire.")
    @Column(nullable = false, length = 255)
    private String type;

    /** 👤 Utilisateur qui a généré le rapport */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "generated_by", nullable = false)
    @NotNull(message = "L'utilisateur générateur du rapport est obligatoire.")
    private User generatedBy;

    /** 🕒 Date de génération du rapport */
    @Column(nullable = false)
    private LocalDateTime date;

    /** 🧮 Contenu du rapport au format JSON (par ex : {"ventes":5,"revenu":35000000}) */
    @NotNull(message = "Le contenu du rapport est obligatoire.")
    @Column(columnDefinition = "jsonb", nullable = false)
    private String content;

    /** Définition automatique de la date si non fournie */
    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", generatedBy=" + (generatedBy != null ? generatedBy.getId() : null) +
                ", date=" + date +
                '}';
    }
}
