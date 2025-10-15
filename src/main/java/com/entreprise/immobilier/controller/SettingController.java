package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.SettingDTO;
import com.entreprise.immobilier.model.Setting;
import com.entreprise.immobilier.service.interfaces.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public List<Setting> getAllSettings() {
        return settingService.getAllSettings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setting> getSettingById(@PathVariable Long id) {
        return settingService.getSettingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Setting> getSettingByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(settingService.getSettingByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Setting> createSetting(@Valid @RequestBody SettingDTO dto) {
        return ResponseEntity.ok(settingService.createSetting(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setting> updateSetting(@PathVariable Long id, @Valid @RequestBody SettingDTO dto) {
        return ResponseEntity.ok(settingService.updateSetting(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        settingService.deleteSetting(id);
        return ResponseEntity.noContent().build();
    }
}
