package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.StatisticDTO;
import com.entreprise.immobilier.model.Statistic;

import java.util.List;
import java.util.Optional;

public interface StatisticService {
    List<Statistic> getAllStatistics();
    Optional<Statistic> getStatisticById(Long id);
    List<Statistic> getStatisticsByType(String type);
    Statistic createStatistic(StatisticDTO dto);
    Statistic updateStatistic(Long id, StatisticDTO dto);
    void deleteStatistic(Long id);
}
