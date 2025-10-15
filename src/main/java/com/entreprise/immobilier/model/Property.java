package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "properties", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 🏷️ Titre de l’annonce */
    @NotBlank(message = "Le titre est obligatoire.")
    @Column(nullable = false)
    private String title;

    /** 📝 Description du bien */
    private String description;

    /** 💰 Prix du bien */
    @Min(value = 1, message = "Le prix doit être supérieur à zéro.")
    @Column(nullable = false)
    private double price;

    /** 📐 Taille du bien */
    @Min(value = 1, message = "La taille doit être positive.")
    @Column(nullable = false)
    private int size;

    /** 📍 Adresse du bien */
    @NotBlank(message = "L'adresse est obligatoire.")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "La ville est obligatoire.")
    private String city;

    @NotBlank(message = "Le code postal est obligatoire.")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    /** 🏠 Type du bien */
    @Pattern(regexp = "^(appartement|maison|terrain)$", message = "Type invalide.")
    @Column(nullable = false)
    private String type;

    /** 📊 Statut du bien */
    @Pattern(regexp = "^(à vendre|à louer|vendu|loué)$", message = "Statut invalide.")
    @Column(nullable = false)
    private String status;

    /** 🕒 Dates automatiques */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** 👨‍💼 Agent associé */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                '}';
    }
}
