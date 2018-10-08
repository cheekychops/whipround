package org.cheekylogic.whipround.service.mapper;

import org.cheekylogic.whipround.domain.*;
import org.cheekylogic.whipround.service.dto.PreapprovalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Preapproval and its DTO PreapprovalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PreapprovalMapper extends EntityMapper<PreapprovalDTO, Preapproval> {


    @Mapping(target = "contribution", ignore = true)
    Preapproval toEntity(PreapprovalDTO preapprovalDTO);

    default Preapproval fromId(Long id) {
        if (id == null) {
            return null;
        }
        Preapproval preapproval = new Preapproval();
        preapproval.setId(id);
        return preapproval;
    }
}
