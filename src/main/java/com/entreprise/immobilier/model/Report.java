package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports", schema = "audit")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "generated_by")
    private User generatedBy;

    private LocalDateTime date = LocalDateTime.now();

    @Column(columnDefinition = "json")
    private String content;
}
