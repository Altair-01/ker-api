package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_tokens", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 👤 L’utilisateur associé au token */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "L'utilisateur est obligatoire.")
    private User user;

    /** 🔑 Le token d’authentification (souvent JWT ou session unique) */
    @NotBlank(message = "Le token est obligatoire.")
    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String token;

    /** ⏳ Date d’expiration du token */
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    /** 🕒 Date de création du token (utile pour audit) */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** ✅ Statut : actif ou expiré */
    @Column(nullable = false)
    private boolean active = true;

    /** Définition automatique des dates */
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", active=" + active +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
