package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.FavoriteDTO;
import com.entreprise.immobilier.model.Favorite;
import com.entreprise.immobilier.model.Property;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.FavoriteRepository;
import com.entreprise.immobilier.repository.PropertyRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.FavoriteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    @Override
    public Optional<Favorite> getFavoriteById(Long id) {
        return favoriteRepository.findById(id);
    }

    @Override
    public Favorite createFavorite(FavoriteDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien introuvable."));

        if (favoriteRepository.existsByUserAndProperty(user, property)) {
            throw new IllegalStateException("Ce bien est déjà dans les favoris de l'utilisateur.");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .property(property)
                .build();

        return favoriteRepository.save(favorite);
    }

    @Override
    public void deleteFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new EntityNotFoundException("Favori introuvable.");
        }
        favoriteRepository.deleteById(id);
    }

    @Override
    public List<Favorite> getFavoritesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return favoriteRepository.findByUser(user);
    }
}
