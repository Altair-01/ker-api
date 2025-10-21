package com.entreprise.immobilier.model;

/**
 * Statut du bien immobilier (valeurs stockées en base en anglais).
 */
public enum PropertyStatus {
    for_sale,   // À vendre
    for_rent,   // À louer
    sold,       // Vendu
    rented;     // Loué

    /**
     * Permet de retrouver un statut à partir d'une chaîne (insensible à la casse).
     */
    public static PropertyStatus fromValue(String value) {
        for (PropertyStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Statut invalide : " + value);
    }
}
