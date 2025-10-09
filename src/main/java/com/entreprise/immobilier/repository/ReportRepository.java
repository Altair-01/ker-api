package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 🔹 REPORTS
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByType(String type);
}
