package org.cheekylogic.whipround.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.cheekylogic.whipround.service.PreapprovalService;
import org.cheekylogic.whipround.web.rest.errors.BadRequestAlertException;
import org.cheekylogic.whipround.web.rest.util.HeaderUtil;
import org.cheekylogic.whipround.service.dto.PreapprovalDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Preapproval.
 */
@RestController
@RequestMapping("/api")
public class PreapprovalResource {

    private final Logger log = LoggerFactory.getLogger(PreapprovalResource.class);

    private static final String ENTITY_NAME = "preapproval";

    private final PreapprovalService preapprovalService;

    public PreapprovalResource(PreapprovalService preapprovalService) {
        this.preapprovalService = preapprovalService;
    }

    /**
     * POST  /preapprovals : Create a new preapproval.
     *
     * @param preapprovalDTO the preapprovalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preapprovalDTO, or with status 400 (Bad Request) if the preapproval has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/preapprovals")
    @Timed
    public ResponseEntity<PreapprovalDTO> createPreapproval(@RequestBody PreapprovalDTO preapprovalDTO) throws URISyntaxException {
        log.debug("REST request to save Preapproval : {}", preapprovalDTO);
        if (preapprovalDTO.getId() != null) {
            throw new BadRequestAlertException("A new preapproval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreapprovalDTO result = preapprovalService.save(preapprovalDTO);
        return ResponseEntity.created(new URI("/api/preapprovals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /preapprovals : Updates an existing preapproval.
     *
     * @param preapprovalDTO the preapprovalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preapprovalDTO,
     * or with status 400 (Bad Request) if the preapprovalDTO is not valid,
     * or with status 500 (Internal Server Error) if the preapprovalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/preapprovals")
    @Timed
    public ResponseEntity<PreapprovalDTO> updatePreapproval(@RequestBody PreapprovalDTO preapprovalDTO) throws URISyntaxException {
        log.debug("REST request to update Preapproval : {}", preapprovalDTO);
        if (preapprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreapprovalDTO result = preapprovalService.save(preapprovalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preapprovalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /preapprovals : get all the preapprovals.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of preapprovals in body
     */
    @GetMapping("/preapprovals")
    @Timed
    public List<PreapprovalDTO> getAllPreapprovals(@RequestParam(required = false) String filter) {
        if ("contribution-is-null".equals(filter)) {
            log.debug("REST request to get all Preapprovals where contribution is null");
            return preapprovalService.findAllWhereContributionIsNull();
        }
        log.debug("REST request to get all Preapprovals");
        return preapprovalService.findAll();
    }

    /**
     * GET  /preapprovals/:id : get the "id" preapproval.
     *
     * @param id the id of the preapprovalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preapprovalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/preapprovals/{id}")
    @Timed
    public ResponseEntity<PreapprovalDTO> getPreapproval(@PathVariable Long id) {
        log.debug("REST request to get Preapproval : {}", id);
        Optional<PreapprovalDTO> preapprovalDTO = preapprovalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preapprovalDTO);
    }

    /**
     * DELETE  /preapprovals/:id : delete the "id" preapproval.
     *
     * @param id the id of the preapprovalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/preapprovals/{id}")
    @Timed
    public ResponseEntity<Void> deletePreapproval(@PathVariable Long id) {
        log.debug("REST request to delete Preapproval : {}", id);
        preapprovalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
