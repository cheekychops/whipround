package org.cheekylogic.whipround.service.impl;

import org.cheekylogic.whipround.service.WhiproundService;
import org.cheekylogic.whipround.domain.Whipround;
import org.cheekylogic.whipround.repository.WhiproundRepository;
import org.cheekylogic.whipround.service.dto.WhiproundDTO;
import org.cheekylogic.whipround.service.mapper.WhiproundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Whipround.
 */
@Service
@Transactional
public class WhiproundServiceImpl implements WhiproundService {

    private final Logger log = LoggerFactory.getLogger(WhiproundServiceImpl.class);

    private final WhiproundRepository whiproundRepository;

    private final WhiproundMapper whiproundMapper;

    public WhiproundServiceImpl(WhiproundRepository whiproundRepository, WhiproundMapper whiproundMapper) {
        this.whiproundRepository = whiproundRepository;
        this.whiproundMapper = whiproundMapper;
    }

    /**
     * Save a whipround.
     *
     * @param whiproundDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WhiproundDTO save(WhiproundDTO whiproundDTO) {
        log.debug("Request to save Whipround : {}", whiproundDTO);
        Whipround whipround = whiproundMapper.toEntity(whiproundDTO);
        whipround = whiproundRepository.save(whipround);
        return whiproundMapper.toDto(whipround);
    }

    /**
     * Get all the whiprounds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WhiproundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Whiprounds");
        return whiproundRepository.findAll(pageable)
            .map(whiproundMapper::toDto);
    }

    /**
     * Get all the Whipround with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<WhiproundDTO> findAllWithEagerRelationships(Pageable pageable) {
        return whiproundRepository.findAllWithEagerRelationships(pageable).map(whiproundMapper::toDto);
    }
    

    /**
     * Get one whipround by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WhiproundDTO> findOne(Long id) {
        log.debug("Request to get Whipround : {}", id);
        return whiproundRepository.findOneWithEagerRelationships(id)
            .map(whiproundMapper::toDto);
    }

    /**
     * Delete the whipround by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Whipround : {}", id);
        whiproundRepository.deleteById(id);
    }
}
