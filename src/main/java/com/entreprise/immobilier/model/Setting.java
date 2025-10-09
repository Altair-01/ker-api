package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings", schema = "config")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "notifications_enabled")
    private boolean notificationsEnabled = true;

    private String language = "fr";

    @Column(name = "theme")
    private String theme = "clair";
}
