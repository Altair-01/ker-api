package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.ContractDTO;
import com.entreprise.immobilier.model.*;
import com.entreprise.immobilier.repository.*;
import com.entreprise.immobilier.service.interfaces.ContractService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final AgentRepository agentRepository;

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    @Override
    public Contract createContract(ContractDTO dto) {
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        User client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));
        Agent agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));

        Contract contract = Contract.builder()
                .property(property)
                .client(client)
                .agent(agent)
                .type(dto.getType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .price(dto.getPrice())
                .status(dto.getStatus())
                .build();

        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, ContractDTO dto) {
        Contract existingContract = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable."));

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier introuvable."));
        User client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));
        Agent agent = agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));

        existingContract.setProperty(property);
        existingContract.setClient(client);
        existingContract.setAgent(agent);
        existingContract.setType(dto.getType());
        existingContract.setStartDate(dto.getStartDate());
        existingContract.setEndDate(dto.getEndDate());
        existingContract.setPrice(dto.getPrice());
        existingContract.setStatus(dto.getStatus());

        return contractRepository.save(existingContract);
    }

    @Override
    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable.");
        }
        contractRepository.deleteById(id);
    }

    @Override
    public List<Contract> getContractsByAgent(Long agentId) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("Agent introuvable."));
        return contractRepository.findByAgent(agent);
    }

    @Override
    public List<Contract> getContractsByClient(Long clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable."));
        return contractRepository.findByClient(client);
    }
}
