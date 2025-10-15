package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "settings", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 👤 L’utilisateur auquel ces paramètres appartiennent */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "L'utilisateur est obligatoire.")
    private User user;

    /** 🔔 Indique si les notifications sont activées */
    @Column(name = "notifications_enabled", nullable = false)
    private Boolean notificationsEnabled = true;

    /** 🌍 Langue de l’utilisateur */
    @Column(nullable = false, length = 50)
    private String language = "fr";

    /** 🎨 Thème d’affichage ("clair" ou "sombre") */
    @Column(nullable = false, length = 50)
    private String theme = "clair";

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", notificationsEnabled=" + notificationsEnabled +
                ", language='" + language + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }
}
