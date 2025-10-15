package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Report;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByGeneratedBy(User user);
    List<Report> findByType(String type);
}
