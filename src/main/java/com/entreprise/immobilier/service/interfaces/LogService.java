package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.LogDTO;
import com.entreprise.immobilier.model.Log;

import java.util.List;
import java.util.Optional;

public interface LogService {
    List<Log> getAllLogs();
    Optional<Log> getLogById(Long id);
    Log createLog(LogDTO dto);
    void deleteLog(Long id);
    List<Log> getLogsByUser(Long userId);
}
