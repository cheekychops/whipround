package org.cheekylogic.whipround.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.cheekylogic.whipround.service.WhiproundService;
import org.cheekylogic.whipround.web.rest.errors.BadRequestAlertException;
import org.cheekylogic.whipround.web.rest.util.HeaderUtil;
import org.cheekylogic.whipround.web.rest.util.PaginationUtil;
import org.cheekylogic.whipround.service.dto.WhiproundDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Whipround.
 */
@RestController
@RequestMapping("/api")
public class WhiproundResource {

    private final Logger log = LoggerFactory.getLogger(WhiproundResource.class);

    private static final String ENTITY_NAME = "whipround";

    private final WhiproundService whiproundService;

    public WhiproundResource(WhiproundService whiproundService) {
        this.whiproundService = whiproundService;
    }

    /**
     * POST  /whiprounds : Create a new whipround.
     *
     * @param whiproundDTO the whiproundDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new whiproundDTO, or with status 400 (Bad Request) if the whipround has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/whiprounds")
    @Timed
    public ResponseEntity<WhiproundDTO> createWhipround(@RequestBody WhiproundDTO whiproundDTO) throws URISyntaxException {
        log.debug("REST request to save Whipround : {}", whiproundDTO);
        if (whiproundDTO.getId() != null) {
            throw new BadRequestAlertException("A new whipround cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WhiproundDTO result = whiproundService.save(whiproundDTO);
        return ResponseEntity.created(new URI("/api/whiprounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /whiprounds : Updates an existing whipround.
     *
     * @param whiproundDTO the whiproundDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated whiproundDTO,
     * or with status 400 (Bad Request) if the whiproundDTO is not valid,
     * or with status 500 (Internal Server Error) if the whiproundDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/whiprounds")
    @Timed
    public ResponseEntity<WhiproundDTO> updateWhipround(@RequestBody WhiproundDTO whiproundDTO) throws URISyntaxException {
        log.debug("REST request to update Whipround : {}", whiproundDTO);
        if (whiproundDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WhiproundDTO result = whiproundService.save(whiproundDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, whiproundDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /whiprounds : get all the whiprounds.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of whiprounds in body
     */
    @GetMapping("/whiprounds")
    @Timed
    public ResponseEntity<List<WhiproundDTO>> getAllWhiprounds(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Whiprounds");
        Page<WhiproundDTO> page;
        if (eagerload) {
            page = whiproundService.findAllWithEagerRelationships(pageable);
        } else {
            page = whiproundService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/whiprounds?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /whiprounds/:id : get the "id" whipround.
     *
     * @param id the id of the whiproundDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the whiproundDTO, or with status 404 (Not Found)
     */
    @GetMapping("/whiprounds/{id}")
    @Timed
    public ResponseEntity<WhiproundDTO> getWhipround(@PathVariable Long id) {
        log.debug("REST request to get Whipround : {}", id);
        Optional<WhiproundDTO> whiproundDTO = whiproundService.findOne(id);
        return ResponseUtil.wrapOrNotFound(whiproundDTO);
    }

    /**
     * DELETE  /whiprounds/:id : delete the "id" whipround.
     *
     * @param id the id of the whiproundDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/whiprounds/{id}")
    @Timed
    public ResponseEntity<Void> deleteWhipround(@PathVariable Long id) {
        log.debug("REST request to delete Whipround : {}", id);
        whiproundService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
