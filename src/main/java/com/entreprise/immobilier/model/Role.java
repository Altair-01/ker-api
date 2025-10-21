package com.entreprise.immobilier.model;

/**
 * 🎯 Enum représentant les rôles disponibles dans le système KER.
 * Ces rôles définissent les autorisations d'accès des utilisateurs.
 */
public enum Role {

    /** 👑 Administrateur : accès total à toutes les fonctionnalités */
    ADMIN,

    /** 🏢 Agent immobilier : peut gérer ses propres annonces */
    AGENT,

    /** 👤 Client : peut uniquement consulter et contacter les agents */
    CLIENT;

    /**
     * ✅ Retourne la valeur formatée pour Spring Security
     * Exemple : ADMIN → "ROLE_ADMIN"
     */
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
