package br.com.presto.service;

import br.com.presto.domain.Endereco;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Endereco.
 */
public interface EnderecoService {

    /**
     * Save a endereco.
     *
     * @param endereco the entity to save
     * @return the persisted entity
     */
    Endereco save(Endereco endereco);

    /**
     * Get all the enderecos.
     *
     * @return the list of entities
     */
    List<Endereco> findAll();


    /**
     * Get the "id" endereco.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Endereco> findOne(Long id);

    /**
     * Delete the "id" endereco.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
