package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import java.util.Map;

@Entity
@Table(name = "searches", schema = "core")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "json")
    private String criteria;

    private boolean saved = false;
}
