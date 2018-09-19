package br.com.presto.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.presto.domain.Endereco;
import br.com.presto.service.EnderecoService;
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
 * REST controller for managing Endereco.
 */
@RestController
@RequestMapping("/api")
public class EnderecoResource {

    private final Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    private static final String ENTITY_NAME = "endereco";

    private final EnderecoService enderecoService;

    public EnderecoResource(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    /**
     * POST  /enderecos : Create a new endereco.
     *
     * @param endereco the endereco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new endereco, or with status 400 (Bad Request) if the endereco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enderecos")
    @Timed
    public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to save Endereco : {}", endereco);
        if (endereco.getId() != null) {
            throw new BadRequestAlertException("A new endereco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Endereco result = enderecoService.save(endereco);
        return ResponseEntity.created(new URI("/api/enderecos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enderecos : Updates an existing endereco.
     *
     * @param endereco the endereco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated endereco,
     * or with status 400 (Bad Request) if the endereco is not valid,
     * or with status 500 (Internal Server Error) if the endereco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enderecos")
    @Timed
    public ResponseEntity<Endereco> updateEndereco(@RequestBody Endereco endereco) throws URISyntaxException {
        log.debug("REST request to update Endereco : {}", endereco);
        if (endereco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Endereco result = enderecoService.save(endereco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, endereco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enderecos : get all the enderecos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enderecos in body
     */
    @GetMapping("/enderecos")
    @Timed
    public List<Endereco> getAllEnderecos() {
        log.debug("REST request to get all Enderecos");
        return enderecoService.findAll();
    }

    /**
     * GET  /enderecos/:id : get the "id" endereco.
     *
     * @param id the id of the endereco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the endereco, or with status 404 (Not Found)
     */
    @GetMapping("/enderecos/{id}")
    @Timed
    public ResponseEntity<Endereco> getEndereco(@PathVariable Long id) {
        log.debug("REST request to get Endereco : {}", id);
        Optional<Endereco> endereco = enderecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endereco);
    }

    /**
     * DELETE  /enderecos/:id : delete the "id" endereco.
     *
     * @param id the id of the endereco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enderecos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
        log.debug("REST request to delete Endereco : {}", id);
        enderecoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
