package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 🔹 SETTINGS
@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByUserId(Long userId);
}
