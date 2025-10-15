package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🏠 Bien concerné */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /** 👤 Utilisateur ayant laissé l’avis */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** ⭐ Note (entre 1 et 5) */
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int rating;

    /** 💬 Commentaire de l’utilisateur */
    private String comment;

    /** 🕒 Date de publication */
    @Column(nullable = false)
    private LocalDateTime date;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", propertyId=" + (property != null ? property.getId() : null) +
                ", userId=" + (user != null ? user.getId() : null) +
                ", rating=" + rating +
                '}';
    }
}
