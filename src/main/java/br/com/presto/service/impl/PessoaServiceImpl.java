package br.com.presto.service.impl;

import br.com.presto.service.PessoaService;
import br.com.presto.domain.Pessoa;
import br.com.presto.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Pessoa.
 */
@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaServiceImpl.class);

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoa the entity to save
     * @return the persisted entity
     */
    @Override
    public Pessoa save(Pessoa pessoa) {
        log.debug("Request to save Pessoa : {}", pessoa);        return pessoaRepository.save(pessoa);
    }

    /**
     * Get all the pessoas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pessoa> findAll() {
        log.debug("Request to get all Pessoas");
        return pessoaRepository.findAll();
    }


    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pessoa> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }
}
