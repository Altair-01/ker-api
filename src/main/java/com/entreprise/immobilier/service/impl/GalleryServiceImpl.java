package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.GalleryDTO;
import com.entreprise.immobilier.model.Gallery;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.repository.GalleryRepository;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.service.interfaces.GalleryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public List<Gallery> getAllGalleries() {
        return galleryRepository.findAll();
    }

    @Override
    public Optional<Gallery> getGalleryById(Long id) {
        return galleryRepository.findById(id);
    }

    @Override
    public Gallery createGallery(GalleryDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));

        // Si isMain = true, désactiver les autres images principales du même bien
        if (dto.isMain()) {
            List<Gallery> mainImages = galleryRepository.findByPropertyAndIsMainTrue(property);
            mainImages.forEach(img -> img.setMain(false));
            galleryRepository.saveAll(mainImages);
        }

        Gallery gallery = Gallery.builder()
                .property(property)
                .imageUrl(dto.getImageUrl())
                .isMain(dto.isMain())
                .build();

        return galleryRepository.save(gallery);
    }

    @Override
    public Gallery updateGallery(Long id, GalleryDTO dto) {
        Gallery existing = galleryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image introuvable."));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));

        if (dto.isMain()) {
            List<Gallery> mainImages = galleryRepository.findByPropertyAndIsMainTrue(property);
            mainImages.forEach(img -> img.setMain(false));
            galleryRepository.saveAll(mainImages);
        }

        existing.setProperty(property);
        existing.setImageUrl(dto.getImageUrl());
        existing.setMain(dto.isMain());

        return galleryRepository.save(existing);
    }

    @Override
    public void deleteGallery(Long id) {
        if (!galleryRepository.existsById(id)) {
            throw new EntityNotFoundException("Image introuvable.");
        }
        galleryRepository.deleteById(id);
    }

    @Override
    public List<Gallery> getGalleryByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        return galleryRepository.findByProperty(property);
    }
}
