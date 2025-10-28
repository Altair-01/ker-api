package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.mapper.PropertyMapper;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.service.interfaces.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    /** 🔹 Récupérer tous les biens */
    @Override
    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    /** 🔹 Récupérer un bien par ID */
    @Override
    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bien introuvable"));

        // ⚠️ Force le chargement des images (si LAZY)
        property.getGallery().size();

        return propertyMapper.toDTO(property);
    }


    /** 🔹 Créer un nouveau bien */
    @Override
    public PropertyDTO createProperty(PropertyDTO dto) {
        Property property = propertyMapper.toEntity(dto);
        Property saved = propertyRepository.save(property);
        return propertyMapper.toDTO(saved);
    }

    /** 🔹 Supprimer un bien */
    @Override
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new EntityNotFoundException("Propriété non trouvée avec l'ID : " + id);
        }
        propertyRepository.deleteById(id);
    }

    /** 🔹 Rechercher des biens par critères dynamiques */
    @Override
    public List<PropertyDTO> searchProperties(String city, PropertyType type, PropertyStatus status, Double minPrice, Double maxPrice) {
        return propertyRepository.search(city, type, status, minPrice, maxPrice)
                .stream()
                .map(propertyMapper::toDTO)
                .collect(Collectors.toList());
    }

}
