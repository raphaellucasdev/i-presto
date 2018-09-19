package br.com.presto.service;

import br.com.presto.domain.Pessoa;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Pessoa.
 */
public interface PessoaService {

    /**
     * Save a pessoa.
     *
     * @param pessoa the entity to save
     * @return the persisted entity
     */
    Pessoa save(Pessoa pessoa);

    /**
     * Get all the pessoas.
     *
     * @return the list of entities
     */
    List<Pessoa> findAll();


    /**
     * Get the "id" pessoa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Pessoa> findOne(Long id);

    /**
     * Delete the "id" pessoa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
