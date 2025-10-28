package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;
import com.entreprise.immobilier.service.interfaces.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    /** ✅ Obtenir toutes les propriétés */
    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    /** ✅ Obtenir une propriété par ID */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    /** ✅ Créer une propriété (réservé aux agents et admins) */
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
    }

    /** 🚫 Supprimer une propriété (réservé aux admins uniquement) */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    /** 🔍 Recherche avancée */
    @GetMapping("/search")
    public ResponseEntity<List<PropertyDTO>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        PropertyType typeEnum = (type != null && !type.isBlank())
                ? PropertyType.fromValue(type)
                : null;

        PropertyStatus statusEnum = (status != null && !status.isBlank())
                ? PropertyStatus.fromValue(status)
                : null;

        List<PropertyDTO> results = propertyService.searchProperties(city, typeEnum, statusEnum, minPrice, maxPrice);
        return ResponseEntity.ok(results);
    }

}
