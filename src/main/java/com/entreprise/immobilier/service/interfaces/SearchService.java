package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.SearchDTO;
import com.entreprise.immobilier.model.Search;

import java.util.List;
import java.util.Optional;

public interface SearchService {

    List<Search> getAllSearches();

    Optional<Search> getSearchById(Long id);

    Search createSearch(SearchDTO dto);

    Search updateSearch(Long id, SearchDTO dto);

    void deleteSearch(Long id);

    List<Search> getSearchesByUser(Long userId);

    List<Search> getSavedSearches(Long userId);
}
