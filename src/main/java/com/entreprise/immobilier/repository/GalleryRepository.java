package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByPropertyId(Long propertyId);
}
