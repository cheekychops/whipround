package org.cheekylogic.whipround.service.impl;

import org.cheekylogic.whipround.service.ContributionService;
import org.cheekylogic.whipround.domain.Contribution;
import org.cheekylogic.whipround.repository.ContributionRepository;
import org.cheekylogic.whipround.service.dto.ContributionDTO;
import org.cheekylogic.whipround.service.mapper.ContributionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing Contribution.
 */
@Service
@Transactional
public class ContributionServiceImpl implements ContributionService {

    private final Logger log = LoggerFactory.getLogger(ContributionServiceImpl.class);

    private final ContributionRepository contributionRepository;

    private final ContributionMapper contributionMapper;

    public ContributionServiceImpl(ContributionRepository contributionRepository, ContributionMapper contributionMapper) {
        this.contributionRepository = contributionRepository;
        this.contributionMapper = contributionMapper;
    }

    /**
     * Save a contribution.
     *
     * @param contributionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContributionDTO save(ContributionDTO contributionDTO) {
        log.debug("Request to save Contribution : {}", contributionDTO);
        Contribution contribution = contributionMapper.toEntity(contributionDTO);
        contribution = contributionRepository.save(contribution);
        return contributionMapper.toDto(contribution);
    }

    /**
     * Get all the contributions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContributionDTO> findAll() {
        log.debug("Request to get all Contributions");
        return contributionRepository.findAll().stream()
            .map(contributionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one contribution by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContributionDTO> findOne(Long id) {
        log.debug("Request to get Contribution : {}", id);
        return contributionRepository.findById(id)
            .map(contributionMapper::toDto);
    }

    /**
     * Delete the contribution by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contribution : {}", id);
        contributionRepository.deleteById(id);
    }
}
