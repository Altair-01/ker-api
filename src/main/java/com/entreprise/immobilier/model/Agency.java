package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agencies", schema = "core")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String phoneNumber;
    private String email;
    private String website;
}
