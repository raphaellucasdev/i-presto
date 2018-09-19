package br.com.presto.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.presto.domain.Prestador;
import br.com.presto.service.PrestadorService;
import br.com.presto.web.rest.errors.BadRequestAlertException;
import br.com.presto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prestador.
 */
@RestController
@RequestMapping("/api")
public class PrestadorResource {

    private final Logger log = LoggerFactory.getLogger(PrestadorResource.class);

    private static final String ENTITY_NAME = "prestador";

    private final PrestadorService prestadorService;

    public PrestadorResource(PrestadorService prestadorService) {
        this.prestadorService = prestadorService;
    }

    /**
     * POST  /prestadors : Create a new prestador.
     *
     * @param prestador the prestador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prestador, or with status 400 (Bad Request) if the prestador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prestadors")
    @Timed
    public ResponseEntity<Prestador> createPrestador(@RequestBody Prestador prestador) throws URISyntaxException {
        log.debug("REST request to save Prestador : {}", prestador);
        if (prestador.getId() != null) {
            throw new BadRequestAlertException("A new prestador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prestador result = prestadorService.save(prestador);
        return ResponseEntity.created(new URI("/api/prestadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prestadors : Updates an existing prestador.
     *
     * @param prestador the prestador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prestador,
     * or with status 400 (Bad Request) if the prestador is not valid,
     * or with status 500 (Internal Server Error) if the prestador couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prestadors")
    @Timed
    public ResponseEntity<Prestador> updatePrestador(@RequestBody Prestador prestador) throws URISyntaxException {
        log.debug("REST request to update Prestador : {}", prestador);
        if (prestador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prestador result = prestadorService.save(prestador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prestador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prestadors : get all the prestadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prestadors in body
     */
    @GetMapping("/prestadors")
    @Timed
    public List<Prestador> getAllPrestadors() {
        log.debug("REST request to get all Prestadors");
        return prestadorService.findAll();
    }

    /**
     * GET  /prestadors/:id : get the "id" prestador.
     *
     * @param id the id of the prestador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prestador, or with status 404 (Not Found)
     */
    @GetMapping("/prestadors/{id}")
    @Timed
    public ResponseEntity<Prestador> getPrestador(@PathVariable Long id) {
        log.debug("REST request to get Prestador : {}", id);
        Optional<Prestador> prestador = prestadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prestador);
    }

    /**
     * DELETE  /prestadors/:id : delete the "id" prestador.
     *
     * @param id the id of the prestador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prestadors/{id}")
    @Timed
    public ResponseEntity<Void> deletePrestador(@PathVariable Long id) {
        log.debug("REST request to delete Prestador : {}", id);
        prestadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
