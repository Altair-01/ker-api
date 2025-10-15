package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 👤 Utilisateur concerné */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** 💬 Message de la notification */
    @NotBlank(message = "Le message est obligatoire.")
    @Column(nullable = false)
    private String message;

    /** 🕒 Date de création */
    @Column(nullable = false)
    private LocalDateTime date;

    /** 📬 Statut de lecture */
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
