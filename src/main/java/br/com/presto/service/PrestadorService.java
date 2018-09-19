package br.com.presto.service;

import br.com.presto.domain.Prestador;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Prestador.
 */
public interface PrestadorService {

    /**
     * Save a prestador.
     *
     * @param prestador the entity to save
     * @return the persisted entity
     */
    Prestador save(Prestador prestador);

    /**
     * Get all the prestadors.
     *
     * @return the list of entities
     */
    List<Prestador> findAll();


    /**
     * Get the "id" prestador.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Prestador> findOne(Long id);

    /**
     * Delete the "id" prestador.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
