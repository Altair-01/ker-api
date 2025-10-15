package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.SearchDTO;
import com.entreprise.immobilier.model.Search;
import com.entreprise.immobilier.service.interfaces.SearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/searches")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public List<Search> getAllSearches() {
        return searchService.getAllSearches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Search> getSearchById(@PathVariable Long id) {
        return searchService.getSearchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Search>> getSearchesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(searchService.getSearchesByUser(userId));
    }

    @GetMapping("/user/{userId}/saved")
    public ResponseEntity<List<Search>> getSavedSearches(@PathVariable Long userId) {
        return ResponseEntity.ok(searchService.getSavedSearches(userId));
    }

    @PostMapping
    public ResponseEntity<Search> createSearch(@Valid @RequestBody SearchDTO dto) {
        return ResponseEntity.ok(searchService.createSearch(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Search> updateSearch(@PathVariable Long id, @Valid @RequestBody SearchDTO dto) {
        return ResponseEntity.ok(searchService.updateSearch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearch(@PathVariable Long id) {
        searchService.deleteSearch(id);
        return ResponseEntity.noContent().build();
    }
}
