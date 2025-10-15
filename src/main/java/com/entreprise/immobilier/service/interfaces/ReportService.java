package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.ReportDTO;
import com.entreprise.immobilier.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    List<Report> getAllReports();

    Optional<Report> getReportById(Long id);

    Report createReport(ReportDTO dto);

    Report updateReport(Long id, ReportDTO dto);

    void deleteReport(Long id);

    List<Report> getReportsByUser(Long userId);

    List<Report> getReportsByType(String type);
}
