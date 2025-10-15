package com.entreprise.immobilier.dto;

import com.entreprise.immobilier.model.Agent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDTO {

    private Long id;

    @NotNull(message = "L'ID utilisateur est obligatoire.")
    private Long userId;

    private Long agencyId;

    @NotBlank(message = "Le numéro de licence est obligatoire.")
    private String licenseNumber;

    @Min(value = 0, message = "Le nombre d'années d'expérience ne peut pas être négatif.")
    private Integer experienceYears;

    public static AgentDTO fromEntity(Agent agent) {
        if (agent == null) return null;
        return AgentDTO.builder()
                .id(agent.getId())
                .userId(agent.getUser() != null ? agent.getUser().getId() : null)
                .agencyId(agent.getAgency() != null ? agent.getAgency().getId() : null)
                .licenseNumber(agent.getLicenseNumber())
                .experienceYears(agent.getExperienceYears())
                .build();
    }

    public static Agent toEntity(AgentDTO dto) {
        if (dto == null) return null;
        return Agent.builder()
                .id(dto.getId())
                .licenseNumber(dto.getLicenseNumber())
                .experienceYears(dto.getExperienceYears())
                .build();
    }
}
