package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.GalleryDTO;
import com.entreprise.immobilier.model.Gallery;
import com.entreprise.immobilier.service.interfaces.GalleryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galleries")
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping
    public List<Gallery> getAllGalleries() {
        return galleryService.getAllGalleries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable Long id) {
        return galleryService.getGalleryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Gallery>> getGalleryByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(galleryService.getGalleryByProperty(propertyId));
    }

    @PostMapping
    public ResponseEntity<Gallery> createGallery(@Valid @RequestBody GalleryDTO dto) {
        return ResponseEntity.ok(galleryService.createGallery(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable Long id, @Valid @RequestBody GalleryDTO dto) {
        return ResponseEntity.ok(galleryService.updateGallery(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.noContent().build();
    }
}
