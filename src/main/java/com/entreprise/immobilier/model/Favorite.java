package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "favorites", schema = "core",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "property_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🔗 L’utilisateur qui a ajouté le bien en favori */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "L'utilisateur est obligatoire.")
    private User user;

    /** 🔗 Le bien ajouté aux favoris */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    @NotNull(message = "Le bien immobilier est obligatoire.")
    private Property property;

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", propertyId=" + (property != null ? property.getId() : null) +
                '}';
    }
}
