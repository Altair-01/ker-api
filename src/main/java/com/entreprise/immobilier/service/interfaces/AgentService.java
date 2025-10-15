package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.AgentDTO;
import java.util.List;

public interface AgentService {

    List<AgentDTO> getAllAgents();
    AgentDTO getAgentById(Long id);
    AgentDTO createAgent(AgentDTO agentDTO);
    AgentDTO updateAgent(Long id, AgentDTO agentDTO);
    void deleteAgent(Long id);
}
