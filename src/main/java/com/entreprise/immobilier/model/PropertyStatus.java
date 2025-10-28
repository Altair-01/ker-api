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
     * Convertit une chaîne vers un statut valide, en acceptant les libellés FR et EN.
     */
    public static PropertyStatus fromValue(String value) {
        if (value == null) return null;

        value = value.trim().toLowerCase();

        switch (value) {
            case "à vendre":
            case "a vendre":
            case "for_sale":
            case "forsale":
                return for_sale;

            case "à louer":
            case "a louer":
            case "for_rent":
            case "forrent":
                return for_rent;

            case "vendu":
            case "sold":
                return sold;

            case "loué":
            case "loue":
            case "rented":
                return rented;

            default:
                throw new IllegalArgumentException("Statut invalide : " + value);
        }
    }
}
