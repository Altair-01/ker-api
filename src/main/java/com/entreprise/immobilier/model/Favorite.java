package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites", schema = "core")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id")
    private Property property;
}
