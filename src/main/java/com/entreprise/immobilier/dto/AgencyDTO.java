package com.entreprise.immobilier.dto;

import com.entreprise.immobilier.model.Agency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgencyDTO {

    private Long id;

    @NotBlank(message = "Le nom de l'agence est obligatoire.")
    private String name;

    private String address;

    private String phoneNumber;

    @NotNull(message = "L'identifiant de l'administrateur est obligatoire.")
    private Long adminUserId;

    public static AgencyDTO fromEntity(Agency agency) {
        if (agency == null) return null;
        return AgencyDTO.builder()
                .id(agency.getId())
                .name(agency.getName())
                .address(agency.getAddress())
                .phoneNumber(agency.getPhoneNumber())
                .adminUserId(agency.getAdminUser() != null ? agency.getAdminUser().getId() : null)
                .build();
    }

    public static Agency toEntity(AgencyDTO dto) {
        if (dto == null) return null;
        return Agency.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
