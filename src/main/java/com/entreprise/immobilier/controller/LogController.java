package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.LogDTO;
import com.entreprise.immobilier.model.Log;
import com.entreprise.immobilier.service.interfaces.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> getLogById(@PathVariable Long id) {
        return logService.getLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Log>> getLogsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(logService.getLogsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Log> createLog(@Valid @RequestBody LogDTO dto) {
        return ResponseEntity.ok(logService.createLog(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLog(@PathVariable Long id) {
        logService.deleteLog(id);
        return ResponseEntity.noContent().build();
    }
}
