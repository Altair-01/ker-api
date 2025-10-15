package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.AgencyDTO;
import com.entreprise.immobilier.model.Agency;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.AgencyRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.AgencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;
    private final UserRepository userRepository;

    @Override
    public List<AgencyDTO> getAllAgencies() {
        return agencyRepository.findAll()
                .stream()
                .map(AgencyDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AgencyDTO getAgencyById(Long id) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'ID : " + id));
        return AgencyDTO.fromEntity(agency);
    }

    @Override
    public AgencyDTO createAgency(AgencyDTO dto) {
        if (agencyRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Une agence avec ce nom existe déjà.");
        }

        User adminUser = userRepository.findById(dto.getAdminUserId())
                .orElseThrow(() -> new EntityNotFoundException("Administrateur introuvable."));

        Agency agency = AgencyDTO.toEntity(dto);
        agency.setAdminUser(adminUser);

        return AgencyDTO.fromEntity(agencyRepository.save(agency));
    }

    @Override
    public AgencyDTO updateAgency(Long id, AgencyDTO dto) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable."));

        agency.setName(dto.getName());
        agency.setAddress(dto.getAddress());
        agency.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getAdminUserId() != null) {
            User adminUser = userRepository.findById(dto.getAdminUserId())
                    .orElseThrow(() -> new EntityNotFoundException("Administrateur introuvable."));
            agency.setAdminUser(adminUser);
        }

        return AgencyDTO.fromEntity(agencyRepository.save(agency));
    }

    @Override
    public void deleteAgency(Long id) {
        if (!agencyRepository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable pour suppression.");
        }
        agencyRepository.deleteById(id);
    }
}
