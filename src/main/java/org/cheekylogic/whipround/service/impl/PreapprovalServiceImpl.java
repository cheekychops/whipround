package org.cheekylogic.whipround.service.impl;

import org.cheekylogic.whipround.service.PreapprovalService;
import org.cheekylogic.whipround.domain.Preapproval;
import org.cheekylogic.whipround.repository.PreapprovalRepository;
import org.cheekylogic.whipround.service.dto.PreapprovalDTO;
import org.cheekylogic.whipround.service.mapper.PreapprovalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Service Implementation for managing Preapproval.
 */
@Service
@Transactional
public class PreapprovalServiceImpl implements PreapprovalService {

    private final Logger log = LoggerFactory.getLogger(PreapprovalServiceImpl.class);

    private final PreapprovalRepository preapprovalRepository;

    private final PreapprovalMapper preapprovalMapper;

    public PreapprovalServiceImpl(PreapprovalRepository preapprovalRepository, PreapprovalMapper preapprovalMapper) {
        this.preapprovalRepository = preapprovalRepository;
        this.preapprovalMapper = preapprovalMapper;
    }

    /**
     * Save a preapproval.
     *
     * @param preapprovalDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PreapprovalDTO save(PreapprovalDTO preapprovalDTO) {
        log.debug("Request to save Preapproval : {}", preapprovalDTO);
        Preapproval preapproval = preapprovalMapper.toEntity(preapprovalDTO);
        preapproval = preapprovalRepository.save(preapproval);
        return preapprovalMapper.toDto(preapproval);
    }

    /**
     * Get all the preapprovals.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PreapprovalDTO> findAll() {
        log.debug("Request to get all Preapprovals");
        return preapprovalRepository.findAll().stream()
            .map(preapprovalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  get all the preapprovals where Contribution is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PreapprovalDTO> findAllWhereContributionIsNull() {
        log.debug("Request to get all preapprovals where Contribution is null");
        return StreamSupport
            .stream(preapprovalRepository.findAll().spliterator(), false)
            .filter(preapproval -> preapproval.getContribution() == null)
            .map(preapprovalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one preapproval by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PreapprovalDTO> findOne(Long id) {
        log.debug("Request to get Preapproval : {}", id);
        return preapprovalRepository.findById(id)
            .map(preapprovalMapper::toDto);
    }

    /**
     * Delete the preapproval by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preapproval : {}", id);
        preapprovalRepository.deleteById(id);
    }
}
