package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.GalleryDTO;
import com.entreprise.immobilier.model.Gallery;

import java.util.List;
import java.util.Optional;

public interface GalleryService {

    List<Gallery> getAllGalleries();

    Optional<Gallery> getGalleryById(Long id);

    Gallery createGallery(GalleryDTO dto);

    Gallery updateGallery(Long id, GalleryDTO dto);

    void deleteGallery(Long id);

    List<Gallery> getGalleryByProperty(Long propertyId);
}
