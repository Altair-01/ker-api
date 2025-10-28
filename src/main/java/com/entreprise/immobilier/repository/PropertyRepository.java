package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.PropertyStatus;
import com.entreprise.immobilier.model.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
        SELECT p FROM Property p
        WHERE 
            (:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%')))
        AND (:type IS NULL OR p.type = :type)
        AND (:status IS NULL OR p.status = :status)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """)
    List<Property> search(
            @Param("city") String city,
            @Param("type") PropertyType type,
            @Param("status") PropertyStatus status,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
