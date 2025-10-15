package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.SearchDTO;
import com.entreprise.immobilier.model.Search;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.SearchRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.SearchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final UserRepository userRepository;

    @Override
    public List<Search> getAllSearches() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> getSearchById(Long id) {
        return searchRepository.findById(id);
    }

    @Override
    public Search createSearch(SearchDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        Search search = Search.builder()
                .user(user)
                .criteria(dto.getCriteriaJson())
                .saved(dto.isSaved())
                .createdAt(LocalDateTime.now())
                .build();

        return searchRepository.save(search);
    }

    @Override
    public Search updateSearch(Long id, SearchDTO dto) {
        Search existing = searchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recherche introuvable."));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));

        existing.setUser(user);
        existing.setCriteria(dto.getCriteriaJson());
        existing.setSaved(dto.isSaved());

        return searchRepository.save(existing);
    }

    @Override
    public void deleteSearch(Long id) {
        if (!searchRepository.existsById(id)) {
            throw new EntityNotFoundException("Recherche introuvable.");
        }
        searchRepository.deleteById(id);
    }

    @Override
    public List<Search> getSearchesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return searchRepository.findByUser(user);
    }

    @Override
    public List<Search> getSavedSearches(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable."));
        return searchRepository.findByUserAndSavedTrue(user);
    }
}
