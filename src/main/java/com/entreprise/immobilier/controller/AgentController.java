package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.AgentDTO;
import com.entreprise.immobilier.service.interfaces.AgentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public ResponseEntity<List<AgentDTO>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Long id) {
        return ResponseEntity.ok(agentService.getAgentById(id));
    }

    @PostMapping
    public ResponseEntity<AgentDTO> createAgent(@Valid @RequestBody AgentDTO agentDTO) {
        return ResponseEntity.ok(agentService.createAgent(agentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDTO> updateAgent(@PathVariable Long id, @Valid @RequestBody AgentDTO agentDTO) {
        return ResponseEntity.ok(agentService.updateAgent(id, agentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}
