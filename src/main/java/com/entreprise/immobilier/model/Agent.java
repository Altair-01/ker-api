package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agents", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Relation vers User : chaque agent correspond à un utilisateur */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_agent_user"))
    private User user;

    /** Relation vers Agency : un agent appartient à une agence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", foreignKey = @ForeignKey(name = "fk_agent_agency"))
    private Agency agency;

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "experience_years")
    private Integer experienceYears;

    /**
     * Relation future possible : un agent peut gérer plusieurs propriétés
     * @OneToMany(mappedBy = "agent")
     * private List<Property> properties;
     */
}
