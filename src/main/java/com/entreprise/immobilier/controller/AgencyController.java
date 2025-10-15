package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.AgencyDTO;
import com.entreprise.immobilier.service.interfaces.AgencyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @GetMapping
    public ResponseEntity<List<AgencyDTO>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgencies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDTO> getAgencyById(@PathVariable Long id) {
        return ResponseEntity.ok(agencyService.getAgencyById(id));
    }

    @PostMapping
    public ResponseEntity<AgencyDTO> createAgency(@Valid @RequestBody AgencyDTO agencyDTO) {
        return ResponseEntity.ok(agencyService.createAgency(agencyDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyDTO> updateAgency(@PathVariable Long id, @Valid @RequestBody AgencyDTO agencyDTO) {
        return ResponseEntity.ok(agencyService.updateAgency(id, agencyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}
