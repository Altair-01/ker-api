package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.FavoriteDTO;
import com.entreprise.immobilier.model.Favorite;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    List<Favorite> getAllFavorites();

    Optional<Favorite> getFavoriteById(Long id);

    Favorite createFavorite(FavoriteDTO dto);

    void deleteFavorite(Long id);

    List<Favorite> getFavoritesByUser(Long userId);
}
