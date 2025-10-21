package com.entreprise.immobilier.model;

/**
 * Type de bien immobilier (valeurs stockées en base en anglais).
 */
public enum PropertyType {
    apartment,  // Appartement
    house,      // Maison
    land;       // Terrain

    /**
     * Permet de retrouver un type à partir d'une chaîne (insensible à la casse).
     */
    public static PropertyType fromValue(String value) {
        for (PropertyType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Type de bien invalide : " + value);
    }
}
