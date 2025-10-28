package com.entreprise.immobilier.model;

/**
 * Type de bien immobilier (valeurs stockées en base en anglais).
 */
public enum PropertyType {
    apartment,  // Appartement
    house,      // Maison
    land;       // Terrain

    /**
     * Convertit une chaîne vers un type valide (tolérante, insensible à la casse, FR/EN).
     */
    public static PropertyType fromValue(String value) {
        if (value == null) return null;

        value = value.trim().toLowerCase();

        switch (value) {
            case "appartement":
            case "apartment":
                return apartment;

            case "maison":
            case "house":
                return house;

            case "terrain":
            case "land":
                return land;

            default:
                throw new IllegalArgumentException("Type de bien invalide : " + value);
        }
    }
}
