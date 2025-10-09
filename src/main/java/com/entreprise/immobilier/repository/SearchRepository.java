package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 SEARCHES
@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findByUserId(Long userId);
}
