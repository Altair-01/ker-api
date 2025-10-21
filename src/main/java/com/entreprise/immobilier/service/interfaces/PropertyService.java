package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;

import java.util.List;

public interface PropertyService {

    List<PropertyDTO> getAllProperties();

    PropertyDTO getPropertyById(Long id);

    PropertyDTO createProperty(PropertyDTO dto);

    void deleteProperty(Long id);

    List<PropertyDTO> searchProperties(String city, PropertyType type, PropertyStatus status, Double minPrice, Double maxPrice);
}
