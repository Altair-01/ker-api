package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.ContractDTO;
import com.entreprise.immobilier.model.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    List<Contract> getAllContracts();

    Optional<Contract> getContractById(Long id);

    Contract createContract(ContractDTO dto);

    Contract updateContract(Long id, ContractDTO dto);

    void deleteContract(Long id);

    List<Contract> getContractsByAgent(Long agentId);

    List<Contract> getContractsByClient(Long clientId);
}
