package br.com.presto.service.impl;

import br.com.presto.service.PrestadorService;
import br.com.presto.domain.Prestador;
import br.com.presto.repository.PrestadorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Prestador.
 */
@Service
@Transactional
public class PrestadorServiceImpl implements PrestadorService {

    private final Logger log = LoggerFactory.getLogger(PrestadorServiceImpl.class);

    private final PrestadorRepository prestadorRepository;

    public PrestadorServiceImpl(PrestadorRepository prestadorRepository) {
        this.prestadorRepository = prestadorRepository;
    }

    /**
     * Save a prestador.
     *
     * @param prestador the entity to save
     * @return the persisted entity
     */
    @Override
    public Prestador save(Prestador prestador) {
        log.debug("Request to save Prestador : {}", prestador);        return prestadorRepository.save(prestador);
    }

    /**
     * Get all the prestadors.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Prestador> findAll() {
        log.debug("Request to get all Prestadors");
        return prestadorRepository.findAll();
    }


    /**
     * Get one prestador by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prestador> findOne(Long id) {
        log.debug("Request to get Prestador : {}", id);
        return prestadorRepository.findById(id);
    }

    /**
     * Delete the prestador by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prestador : {}", id);
        prestadorRepository.deleteById(id);
    }
}
