package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.model.Gallery;
import com.entreprise.immobilier.service.impl.GalleryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties/{propertyId}/gallery")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryServiceImpl galleryService;

    /** 📤 Upload d'images pour un bien immobilier */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<?> uploadGallery(
            @PathVariable Long propertyId,
            @RequestParam("files") List<MultipartFile> files) {

        try {
            if (files == null || files.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Aucun fichier reçu")
                );
            }

            if (files.size() > 10) {
                return ResponseEntity.badRequest().body(
                        Map.of("error", "Maximum 10 images autorisées par upload")
                );
            }

            List<Gallery> uploaded = galleryService.uploadGallery(propertyId, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploaded);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Erreur lors de l’enregistrement des images")
            );
        }
    }

    /** 📸 Récupère les images d'un bien */
    @GetMapping
    public ResponseEntity<List<Gallery>> getGallery(@PathVariable Long propertyId) {
        return ResponseEntity.ok(galleryService.getGalleryByProperty(propertyId));
    }

    /** ❌ Supprime une image */
    @DeleteMapping("/{imageId}")
    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    public ResponseEntity<?> deleteGalleryImage(@PathVariable Long imageId) {
        galleryService.deleteGalleryImage(imageId);
        return ResponseEntity.ok(Map.of("message", "Image supprimée avec succès"));
    }
}
