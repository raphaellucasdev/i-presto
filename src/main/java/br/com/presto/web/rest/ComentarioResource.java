package br.com.presto.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.presto.domain.Comentario;
import br.com.presto.service.ComentarioService;
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
 * REST controller for managing Comentario.
 */
@RestController
@RequestMapping("/api")
public class ComentarioResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioResource.class);

    private static final String ENTITY_NAME = "comentario";

    private final ComentarioService comentarioService;

    public ComentarioResource(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    /**
     * POST  /comentarios : Create a new comentario.
     *
     * @param comentario the comentario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentario, or with status 400 (Bad Request) if the comentario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentarios")
    @Timed
    public ResponseEntity<Comentario> createComentario(@RequestBody Comentario comentario) throws URISyntaxException {
        log.debug("REST request to save Comentario : {}", comentario);
        if (comentario.getId() != null) {
            throw new BadRequestAlertException("A new comentario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comentario result = comentarioService.save(comentario);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentarios : Updates an existing comentario.
     *
     * @param comentario the comentario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentario,
     * or with status 400 (Bad Request) if the comentario is not valid,
     * or with status 500 (Internal Server Error) if the comentario couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentarios")
    @Timed
    public ResponseEntity<Comentario> updateComentario(@RequestBody Comentario comentario) throws URISyntaxException {
        log.debug("REST request to update Comentario : {}", comentario);
        if (comentario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Comentario result = comentarioService.save(comentario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comentario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentarios : get all the comentarios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comentarios in body
     */
    @GetMapping("/comentarios")
    @Timed
    public List<Comentario> getAllComentarios() {
        log.debug("REST request to get all Comentarios");
        return comentarioService.findAll();
    }

    /**
     * GET  /comentarios/:id : get the "id" comentario.
     *
     * @param id the id of the comentario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentario, or with status 404 (Not Found)
     */
    @GetMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Comentario> getComentario(@PathVariable Long id) {
        log.debug("REST request to get Comentario : {}", id);
        Optional<Comentario> comentario = comentarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comentario);
    }

    /**
     * DELETE  /comentarios/:id : delete the "id" comentario.
     *
     * @param id the id of the comentario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        log.debug("REST request to delete Comentario : {}", id);
        comentarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
