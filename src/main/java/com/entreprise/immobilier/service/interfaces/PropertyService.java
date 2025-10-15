package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.model.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    List<Property> getAllProperties();
    Optional<Property> getPropertyById(Long id);
    Property createProperty(PropertyDTO dto);
    Property updateProperty(Long id, PropertyDTO dto);
    void deleteProperty(Long id);
    List<Property> getPropertiesByAgent(Long agentId);
    List<Property> getPropertiesByCity(String city);
}
