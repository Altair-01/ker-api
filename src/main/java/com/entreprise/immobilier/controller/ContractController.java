package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.ContractDTO;
import com.entreprise.immobilier.model.Contract;
import com.entreprise.immobilier.service.interfaces.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @GetMapping
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        return contractService.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<Contract>> getContractsByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(contractService.getContractsByAgent(agentId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Contract>> getContractsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(contractService.getContractsByClient(clientId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','AGENT')")
    @PostMapping
    public ResponseEntity<Contract> createContract(@Valid @RequestBody ContractDTO dto) {
        return ResponseEntity.ok(contractService.createContract(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @Valid @RequestBody ContractDTO dto) {
        return ResponseEntity.ok(contractService.updateContract(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
