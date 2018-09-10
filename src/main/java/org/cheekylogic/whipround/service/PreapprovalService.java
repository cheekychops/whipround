package org.cheekylogic.whipround.service;

import org.cheekylogic.whipround.service.dto.PreapprovalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Preapproval.
 */
public interface PreapprovalService {

    /**
     * Save a preapproval.
     *
     * @param preapprovalDTO the entity to save
     * @return the persisted entity
     */
    PreapprovalDTO save(PreapprovalDTO preapprovalDTO);

    /**
     * Get all the preapprovals.
     *
     * @return the list of entities
     */
    List<PreapprovalDTO> findAll();
    /**
     * Get all the PreapprovalDTO where Contribution is null.
     *
     * @return the list of entities
     */
    List<PreapprovalDTO> findAllWhereContributionIsNull();


    /**
     * Get the "id" preapproval.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PreapprovalDTO> findOne(Long id);

    /**
     * Delete the "id" preapproval.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
