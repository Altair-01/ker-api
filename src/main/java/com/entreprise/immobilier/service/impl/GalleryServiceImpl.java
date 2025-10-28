package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.model.Gallery;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.repository.GalleryRepository;
import com.entreprise.immobilier.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GalleryServiceImpl {

    private final PropertyRepository propertyRepository;
    private final GalleryRepository galleryRepository;

    private static final String UPLOAD_DIR = "uploads/";

    /** 📤 Upload d'images de propriété */
    public List<Gallery> uploadGallery(Long propertyId, List<MultipartFile> files) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Propriété non trouvée avec l'ID : " + propertyId));

        Files.createDirectories(Paths.get(UPLOAD_DIR));

        List<Gallery> savedImages = new ArrayList<>();

        boolean isFirst = true; // ✅ marquera la première comme image principale

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

            Gallery image = Gallery.builder()
                    .property(property)
                    .imageUrl("uploads/" + fileName)
                    .isMain(isFirst) // ✅ première image = main
                    .build();

            savedImages.add(galleryRepository.save(image));
            isFirst = false;
        }

        return savedImages;
    }

    /** 📸 Récupérer toutes les images associées à une propriété */
    public List<Gallery> getGalleryByProperty(Long propertyId) {
        return galleryRepository.findByPropertyId(propertyId);
    }

    /** ❌ Supprimer une image */
    public void deleteGalleryImage(Long imageId) {
        Gallery image = galleryRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image introuvable dans la galerie"));

        // Suppression du fichier physique
        Path filePath = Paths.get(UPLOAD_DIR + image.getImageUrl());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("⚠ Impossible de supprimer l'image du disque : " + filePath);
        }

        galleryRepository.deleteById(imageId);
    }
}
