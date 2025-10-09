package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statistics", schema = "audit")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double value;

    private LocalDateTime date = LocalDateTime.now();
}
