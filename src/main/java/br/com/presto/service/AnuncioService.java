package br.com.presto.service;

import br.com.presto.domain.Anuncio;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Anuncio.
 */
public interface AnuncioService {

    /**
     * Save a anuncio.
     *
     * @param anuncio the entity to save
     * @return the persisted entity
     */
    Anuncio save(Anuncio anuncio);

    /**
     * Get all the anuncios.
     *
     * @return the list of entities
     */
    List<Anuncio> findAll();


    /**
     * Get the "id" anuncio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Anuncio> findOne(Long id);

    /**
     * Delete the "id" anuncio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
