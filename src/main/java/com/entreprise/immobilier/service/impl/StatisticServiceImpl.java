package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.StatisticDTO;
import com.entreprise.immobilier.model.Statistic;
import com.entreprise.immobilier.repository.StatisticRepository;
import com.entreprise.immobilier.service.interfaces.StatisticService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public List<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    @Override
    public Optional<Statistic> getStatisticById(Long id) {
        return statisticRepository.findById(id);
    }

    @Override
    public List<Statistic> getStatisticsByType(String type) {
        return statisticRepository.findByType(type);
    }

    @Override
    public Statistic createStatistic(StatisticDTO dto) {
        Statistic stat = Statistic.builder()
                .type(dto.getType())
                .value(dto.getValue())
                .date(dto.getDate())
                .build();

        return statisticRepository.save(stat);
    }

    @Override
    public Statistic updateStatistic(Long id, StatisticDTO dto) {
        Statistic existing = statisticRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Statistique introuvable."));

        existing.setType(dto.getType());
        existing.setValue(dto.getValue());
        existing.setDate(dto.getDate());

        return statisticRepository.save(existing);
    }

    @Override
    public void deleteStatistic(Long id) {
        if (!statisticRepository.existsById(id)) {
            throw new EntityNotFoundException("Statistique introuvable.");
        }
        statisticRepository.deleteById(id);
    }
}
