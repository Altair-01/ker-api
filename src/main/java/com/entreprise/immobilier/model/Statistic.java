package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔢 Type de statistique (ex : ventes, visites, agents actifs, etc.) */
    @NotBlank(message = "Le type est obligatoire.")
    @Column(nullable = false)
    private String type;

    /** 📈 Valeur de la statistique */
    @NotNull(message = "La valeur est obligatoire.")
    private double value;

    /** 🕒 Date de mesure */
    @Column(nullable = false)
    private LocalDateTime date;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
}
