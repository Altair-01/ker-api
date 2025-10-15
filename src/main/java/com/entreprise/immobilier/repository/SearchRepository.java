package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Search;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findByUser(User user);
    List<Search> findByUserAndSavedTrue(User user);
}
