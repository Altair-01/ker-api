package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 👤 L’utilisateur à l’origine de l’action */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** ⚙️ Type d’action (connexion, modification, suppression, etc.) */
    @NotBlank(message = "L'action est obligatoire.")
    @Column(nullable = false, length = 255)
    private String action;

    /** 🕒 Horodatage automatique */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /** 🧠 Détails supplémentaires de l’action (JSON) */
    @Column(columnDefinition = "jsonb")
    private String details;

    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", userId=" + (user != null ? user.getId() : null) +
                ", timestamp=" + timestamp +
                '}';
    }
}
