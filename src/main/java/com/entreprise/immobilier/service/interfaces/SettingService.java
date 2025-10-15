package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.SettingDTO;
import com.entreprise.immobilier.model.Setting;

import java.util.List;
import java.util.Optional;

public interface SettingService {

    List<Setting> getAllSettings();

    Optional<Setting> getSettingById(Long id);

    Setting getSettingByUser(Long userId);

    Setting createSetting(SettingDTO dto);

    Setting updateSetting(Long id, SettingDTO dto);

    void deleteSetting(Long id);
}
