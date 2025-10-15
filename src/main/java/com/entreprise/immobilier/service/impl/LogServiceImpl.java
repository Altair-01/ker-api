package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.LogDTO;
import com.entreprise.immobilier.model.Log;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.LogRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.LogService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Override
    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    @Override
    public Optional<Log> getLogById(Long id) {
        return logRepository.findById(id);
    }

    @Override
    public Log createLog(LogDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        Log log = Log.builder()
                .user(user)
                .action(dto.getAction())
                .details(dto.getDetails())
                .timestamp(LocalDateTime.now())
                .build();

        return logRepository.save(log);
    }

    @Override
    public void deleteLog(Long id) {
        if (!logRepository.existsById(id))
            throw new EntityNotFoundException("Log introuvable.");
        logRepository.deleteById(id);
    }

    @Override
    public List<Log> getLogsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return logRepository.findByUser(user);
    }
}
