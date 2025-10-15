package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.AgencyDTO;
import java.util.List;

public interface AgencyService {
    List<AgencyDTO> getAllAgencies();
    AgencyDTO getAgencyById(Long id);
    AgencyDTO createAgency(AgencyDTO agencyDTO);
    AgencyDTO updateAgency(Long id, AgencyDTO agencyDTO);
    void deleteAgency(Long id);
}
