package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.StatisticDTO;
import com.entreprise.immobilier.model.Statistic;
import com.entreprise.immobilier.service.interfaces.StatisticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Statistic> getAllStatistics() {
        return statisticService.getAllStatistics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statistic> getStatisticById(@PathVariable Long id) {
        return statisticService.getStatisticById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Statistic>> getStatisticsByType(@PathVariable String type) {
        return ResponseEntity.ok(statisticService.getStatisticsByType(type));
    }

    @PostMapping
    public ResponseEntity<Statistic> createStatistic(@Valid @RequestBody StatisticDTO dto) {
        return ResponseEntity.ok(statisticService.createStatistic(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Statistic> updateStatistic(@PathVariable Long id, @Valid @RequestBody StatisticDTO dto) {
        return ResponseEntity.ok(statisticService.updateStatistic(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistic(@PathVariable Long id) {
        statisticService.deleteStatistic(id);
        return ResponseEntity.noContent().build();
    }


}
