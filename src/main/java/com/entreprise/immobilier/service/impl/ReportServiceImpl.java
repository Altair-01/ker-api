package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.ReportDTO;
import com.entreprise.immobilier.model.Report;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.ReportRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.ReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public Report createReport(ReportDTO dto) {
        User user = userRepository.findById(dto.getGeneratedById())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur générateur du rapport introuvable."));

        Report report = Report.builder()
                .type(dto.getType())
                .generatedBy(user)
                .content(dto.getContentJson())
                .date(LocalDateTime.now())
                .build();

        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(Long id, ReportDTO dto) {
        Report existing = reportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rapport introuvable."));

        User user = userRepository.findById(dto.getGeneratedById())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur générateur du rapport introuvable."));

        existing.setType(dto.getType());
        existing.setGeneratedBy(user);
        existing.setContent(dto.getContentJson());

        return reportRepository.save(existing);
    }

    @Override
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new EntityNotFoundException("Rapport introuvable.");
        }
        reportRepository.deleteById(id);
    }

    @Override
    public List<Report> getReportsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return reportRepository.findByGeneratedBy(user);
    }

    @Override
    public List<Report> getReportsByType(String type) {
        return reportRepository.findByType(type);
    }
}
