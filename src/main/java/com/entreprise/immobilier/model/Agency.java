package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "agencies", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'agence est obligatoire.")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Email(message = "L'adresse e-mail doit être valide.")
    @Column(unique = true)
    private String email;

    private String website;

    /**
     * 🔗 Relation avec l'utilisateur (administrateur de l'agence)
     * Un utilisateur peut gérer plusieurs agences, mais une agence a un seul admin principal.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", referencedColumnName = "id")
    private User adminUser;

    /**
     * 🔗 Relation avec les agents rattachés à cette agence
     * Si une agence est supprimée, les agents sont "orphelins" (ON DELETE SET NULL)
     */
    @OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Agent> agents;

    @Override
    public String toString() {
        return "Agency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
