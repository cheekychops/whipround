package org.cheekylogic.whipround.service;

import org.cheekylogic.whipround.service.dto.WhiproundDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Whipround.
 */
public interface WhiproundService {

    /**
     * Save a whipround.
     *
     * @param whiproundDTO the entity to save
     * @return the persisted entity
     */
    WhiproundDTO save(WhiproundDTO whiproundDTO);

    /**
     * Get all the whiprounds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WhiproundDTO> findAll(Pageable pageable);

    /**
     * Get all the Whipround with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<WhiproundDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" whipround.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WhiproundDTO> findOne(Long id);

    /**
     * Delete the "id" whipround.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
