package com.entreprise.immobilier.mapper;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PropertyMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** ✅ Convertit une entité vers un DTO */
    public PropertyDTO toDTO(Property property) {
        if (property == null) return null;

        return PropertyDTO.builder()
                .id(property.getId())
                .title(property.getTitle())
                .description(property.getDescription())
                .price(property.getPrice())
                .size(property.getSize())
                .address(property.getAddress())
                .city(property.getCity())
                .postalCode(property.getPostalCode())
                .type(property.getType() != null ? mapTypeToFrench(property.getType()) : null)
                .status(property.getStatus() != null ? mapStatusToFrench(property.getStatus()) : null)
                .agentId(property.getAgent() != null ? property.getAgent().getId() : null)
                .createdAt(property.getCreatedAt() != null ? property.getCreatedAt().format(FORMATTER) : null)
                .updatedAt(property.getUpdatedAt() != null ? property.getUpdatedAt().format(FORMATTER) : null)
                .build();
    }

    /** ✅ Convertit un DTO vers une entité */
    public Property toEntity(PropertyDTO dto) {
        if (dto == null) return null;

        Property property = new Property();
        property.setId(dto.getId());
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPrice(dto.getPrice());
        property.setSize(dto.getSize());
        property.setAddress(dto.getAddress());
        property.setCity(dto.getCity());
        property.setPostalCode(dto.getPostalCode());

        // conversion FR -> Enum anglais
        if (dto.getType() != null) {
            property.setType(mapTypeFromFrench(dto.getType()));
        }
        if (dto.getStatus() != null) {
            property.setStatus(mapStatusFromFrench(dto.getStatus()));
        }

        return property;
    }

    /* 🏠 Mapping TYPE anglais ↔ français */
    private String mapTypeToFrench(PropertyType type) {
        return switch (type) {
            case apartment -> "Appartement";
            case house -> "Maison";
            case land -> "Terrain";
        };
    }

    private PropertyType mapTypeFromFrench(String value) {
        return switch (value.toLowerCase()) {
            case "appartement" -> PropertyType.apartment;
            case "maison" -> PropertyType.house;
            case "terrain" -> PropertyType.land;
            default -> throw new IllegalArgumentException("Type inconnu : " + value);
        };
    }

    /* 📊 Mapping STATUT anglais ↔ français */
    private String mapStatusToFrench(PropertyStatus status) {
        return switch (status) {
            case for_sale -> "À vendre";
            case for_rent -> "À louer";
            case sold -> "Vendu";
            case rented -> "Loué";
        };
    }

    private PropertyStatus mapStatusFromFrench(String value) {
        return switch (value.toLowerCase()) {
            case "à vendre", "a vendre" -> PropertyStatus.for_sale;
            case "à louer", "a louer" -> PropertyStatus.for_rent;
            case "vendu" -> PropertyStatus.sold;
            case "loué", "loue" -> PropertyStatus.rented;
            default -> throw new IllegalArgumentException("Statut inconnu : " + value);
        };
    }
}