package com.linkder.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.linkder.domain.Candidate;
import com.linkder.service.CandidateService;
import com.linkder.web.rest.errors.BadRequestAlertException;
import com.linkder.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Candidate.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {

    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "candidate";

    private final CandidateService candidateService;

    public CandidateResource(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * POST  /candidates : Create a new candidate.
     *
     * @param candidate the candidate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidate, or with status 400 (Bad Request) if the candidate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> createCandidate(@Valid @RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidate);
        if (candidate.getId() != null) {
            throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Candidate result = candidateService.save(candidate);
        return ResponseEntity.created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidates : Updates an existing candidate.
     *
     * @param candidate the candidate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidate,
     * or with status 400 (Bad Request) if the candidate is not valid,
     * or with status 500 (Internal Server Error) if the candidate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidates")
    @Timed
    public ResponseEntity<Candidate> updateCandidate(@Valid @RequestBody Candidate candidate) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidate);
        if (candidate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Candidate result = candidateService.save(candidate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidates : get all the candidates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidates in body
     */
    @GetMapping("/candidates")
    @Timed
    public List<Candidate> getAllCandidates() {
        log.debug("REST request to get all Candidates");
        return candidateService.findAll();
    }

    /**
     * GET  /candidates/:id : get the "id" candidate.
     *
     * @param id the id of the candidate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidate, or with status 404 (Not Found)
     */
    @GetMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Candidate> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        Optional<Candidate> candidate = candidateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidate);
    }

    /**
     * DELETE  /candidates/:id : delete the "id" candidate.
     *
     * @param id the id of the candidate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
