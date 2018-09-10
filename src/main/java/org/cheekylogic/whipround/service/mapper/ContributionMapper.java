package org.cheekylogic.whipround.service.mapper;

import org.cheekylogic.whipround.domain.*;
import org.cheekylogic.whipround.service.dto.ContributionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contribution and its DTO ContributionDTO.
 */
@Mapper(componentModel = "spring", uses = {PreapprovalMapper.class})
public interface ContributionMapper extends EntityMapper<ContributionDTO, Contribution> {

    @Mapping(source = "preapproval.id", target = "preapprovalId")
    @Mapping(source = "preapproval.name", target = "preapprovalName")
    ContributionDTO toDto(Contribution contribution);

    @Mapping(source = "preapprovalId", target = "preapproval")
    @Mapping(target = "whiprounds", ignore = true)
    @Mapping(target = "contributors", ignore = true)
    Contribution toEntity(ContributionDTO contributionDTO);

    default Contribution fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contribution contribution = new Contribution();
        contribution.setId(id);
        return contribution;
    }
}
