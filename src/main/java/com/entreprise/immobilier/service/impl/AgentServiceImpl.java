package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.AgentDTO;
import com.entreprise.immobilier.model.Agent;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.model.Agency;
import com.entreprise.immobilier.repository.AgentRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.repository.AgencyRepository;
import com.entreprise.immobilier.service.interfaces.AgentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;

    @Override
    public List<AgentDTO> getAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(AgentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AgentDTO getAgentById(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent non trouvé avec l'ID : " + id));
        return AgentDTO.fromEntity(agent);
    }

    @Override
    public AgentDTO createAgent(AgentDTO dto) {
        if (agentRepository.existsByLicenseNumber(dto.getLicenseNumber())) {
            throw new IllegalArgumentException("Un agent avec ce numéro de licence existe déjà.");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable pour cet agent."));

        Agency agency = dto.getAgencyId() != null
                ? agencyRepository.findById(dto.getAgencyId())
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable."))
                : null;

        Agent agent = AgentDTO.toEntity(dto);
        agent.setUser(user);
        agent.setAgency(agency);

        return AgentDTO.fromEntity(agentRepository.save(agent));
    }

    @Override
    public AgentDTO updateAgent(Long id, AgentDTO dto) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));

        agent.setLicenseNumber(dto.getLicenseNumber());
        agent.setExperienceYears(dto.getExperienceYears());

        if (dto.getAgencyId() != null) {
            Agency agency = agencyRepository.findById(dto.getAgencyId())
                    .orElseThrow(() -> new EntityNotFoundException("Agence introuvable."));
            agent.setAgency(agency);
        }

        return AgentDTO.fromEntity(agentRepository.save(agent));
    }

    @Override
    public void deleteAgent(Long id) {
        if (!agentRepository.existsById(id)) {
            throw new EntityNotFoundException("Agent introuvable pour suppression.");
        }
        agentRepository.deleteById(id);
    }
}
