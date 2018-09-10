package org.cheekylogic.whipround.service.mapper;

import org.cheekylogic.whipround.domain.*;
import org.cheekylogic.whipround.service.dto.WhiproundDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Whipround and its DTO WhiproundDTO.
 */
@Mapper(componentModel = "spring", uses = {ContributionMapper.class})
public interface WhiproundMapper extends EntityMapper<WhiproundDTO, Whipround> {


    @Mapping(target = "organisers", ignore = true)
    Whipround toEntity(WhiproundDTO whiproundDTO);

    default Whipround fromId(Long id) {
        if (id == null) {
            return null;
        }
        Whipround whipround = new Whipround();
        whipround.setId(id);
        return whipround;
    }
}
