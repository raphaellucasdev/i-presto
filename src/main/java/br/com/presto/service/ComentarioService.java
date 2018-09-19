package br.com.presto.service;

import br.com.presto.domain.Comentario;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Comentario.
 */
public interface ComentarioService {

    /**
     * Save a comentario.
     *
     * @param comentario the entity to save
     * @return the persisted entity
     */
    Comentario save(Comentario comentario);

    /**
     * Get all the comentarios.
     *
     * @return the list of entities
     */
    List<Comentario> findAll();


    /**
     * Get the "id" comentario.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Comentario> findOne(Long id);

    /**
     * Delete the "id" comentario.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
