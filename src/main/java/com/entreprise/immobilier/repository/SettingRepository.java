package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Setting;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByUser(User user);
}
