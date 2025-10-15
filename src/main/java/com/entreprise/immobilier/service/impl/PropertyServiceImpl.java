package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.PropertyDTO;
import com.entreprise.immobilier.model.Agent;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.repository.AgentRepository;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.service.interfaces.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    @Override
    public Property createProperty(PropertyDTO dto) {
        Agent agent = null;
        if (dto.getAgentId() != null) {
            agent = agentRepository.findById(dto.getAgentId())
                    .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));
        }

        Property property = Property.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .size(dto.getSize())
                .address(dto.getAddress())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .type(dto.getType())
                .status(dto.getStatus())
                .agent(agent)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return propertyRepository.save(property);
    }

    @Override
    public Property updateProperty(Long id, PropertyDTO dto) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bien introuvable."));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setSize(dto.getSize());
        existing.setAddress(dto.getAddress());
        existing.setCity(dto.getCity());
        existing.setPostalCode(dto.getPostalCode());
        existing.setType(dto.getType());
        existing.setStatus(dto.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        return propertyRepository.save(existing);
    }

    @Override
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new EntityNotFoundException("Bien introuvable.");
        }
        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> getPropertiesByAgent(Long agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));
        return propertyRepository.findByAgent(agent);
    }

    @Override
    public List<Property> getPropertiesByCity(String city) {
        return propertyRepository.findByCity(city);
    }
}
