package com.entreprise.immobilier.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 🏠 Entité représentant un bien immobilier dans le système KER.
 */
@Entity
@Table(
        name = "properties",
        schema = "core",
        indexes = {
                @Index(name = "idx_property_city", columnList = "city"),
                @Index(name = "idx_property_type", columnList = "type"),
                @Index(name = "idx_property_status", columnList = "status"),
                @Index(name = "idx_property_price", columnList = "price")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🏷️ Titre de l’annonce */
    @NotBlank(message = "Le titre est obligatoire.")
    @Column(nullable = false, length = 150)
    private String title;

    /** 📝 Description du bien */
    @Column(columnDefinition = "TEXT")
    private String description;

    /** 💰 Prix du bien */
    @DecimalMin(value = "1.0", message = "Le prix doit être supérieur à zéro.")
    @Column(nullable = false)
    private Double price;

    /** 📐 Taille (superficie) du bien */
    @Min(value = 1, message = "La taille doit être positive.")
    @Column(nullable = false)
    private Double size;

    /** 📍 Adresse du bien */
    @NotBlank(message = "L'adresse est obligatoire.")
    @Column(nullable = false, length = 255)
    private String address;

    /** 🏙️ Ville */
    @NotBlank(message = "La ville est obligatoire.")
    @Column(nullable = false, length = 100)
    private String city;

    /** 📮 Code postal */
    @NotBlank(message = "Le code postal est obligatoire.")
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    /** 🏠 Type du bien (enum anglais → Apartment, House, Land) */
    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false, length = 20)
    private PropertyType type;

    /** 📊 Statut du bien (enum anglais → for_sale, for_rent, etc.) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PropertyStatus status;

    /** 👨‍💼 Agent associé */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_property_agent"))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Agent agent;

    /** 📅 Dates automatiques */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** ⚙️ Gestion automatique des dates */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /** 🧩 Représentation lisible du bien */
    @Override
    public String toString() {
        return String.format(
                "Property{id=%d, title='%s', price=%.2f, city='%s', status='%s'}",
                id, title, price, city, status
        );
    }


    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Gallery> gallery;


}
