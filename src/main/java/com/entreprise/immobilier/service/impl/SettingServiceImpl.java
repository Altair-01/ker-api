package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.SettingDTO;
import com.entreprise.immobilier.model.Setting;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.SettingRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.SettingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;
    private final UserRepository userRepository;

    @Override
    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    @Override
    public Optional<Setting> getSettingById(Long id) {
        return settingRepository.findById(id);
    }

    @Override
    public Setting getSettingByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return settingRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Paramètres non définis pour cet utilisateur."));
    }

    @Override
    public Setting createSetting(SettingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        Setting setting = Setting.builder()
                .user(user)
                .notificationsEnabled(dto.getNotificationsEnabled())
                .language(dto.getLanguage())
                .theme(dto.getTheme())
                .build();

        return settingRepository.save(setting);
    }

    @Override
    public Setting updateSetting(Long id, SettingDTO dto) {
        Setting existing = settingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paramètres introuvables."));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        existing.setUser(user);
        existing.setNotificationsEnabled(dto.getNotificationsEnabled());
        existing.setLanguage(dto.getLanguage());
        existing.setTheme(dto.getTheme());

        return settingRepository.save(existing);
    }

    @Override
    public void deleteSetting(Long id) {
        if (!settingRepository.existsById(id)) {
            throw new EntityNotFoundException("Paramètres introuvables.");
        }
        settingRepository.deleteById(id);
    }
}
