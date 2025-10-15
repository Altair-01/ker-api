package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "galleries", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🏠 Le bien immobilier auquel appartient l’image */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    /** 🖼️ URL de l’image */
    @NotBlank(message = "L'URL de l'image est obligatoire.")
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    /** 📌 Indique si c’est l’image principale du bien */
    @Column(name = "is_main", nullable = false)
    private boolean isMain = false;

    @Override
    public String toString() {
        return "Gallery{" +
                "id=" + id +
                ", propertyId=" + (property != null ? property.getId() : null) +
                ", isMain=" + isMain +
                '}';
    }
}
