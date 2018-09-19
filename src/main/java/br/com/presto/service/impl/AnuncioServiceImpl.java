package br.com.presto.service.impl;

import br.com.presto.service.AnuncioService;
import br.com.presto.domain.Anuncio;
import br.com.presto.repository.AnuncioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Anuncio.
 */
@Service
@Transactional
public class AnuncioServiceImpl implements AnuncioService {

    private final Logger log = LoggerFactory.getLogger(AnuncioServiceImpl.class);

    private final AnuncioRepository anuncioRepository;

    public AnuncioServiceImpl(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    /**
     * Save a anuncio.
     *
     * @param anuncio the entity to save
     * @return the persisted entity
     */
    @Override
    public Anuncio save(Anuncio anuncio) {
        log.debug("Request to save Anuncio : {}", anuncio);        return anuncioRepository.save(anuncio);
    }

    /**
     * Get all the anuncios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Anuncio> findAll() {
        log.debug("Request to get all Anuncios");
        return anuncioRepository.findAll();
    }


    /**
     * Get one anuncio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Anuncio> findOne(Long id) {
        log.debug("Request to get Anuncio : {}", id);
        return anuncioRepository.findById(id);
    }

    /**
     * Delete the anuncio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anuncio : {}", id);
        anuncioRepository.deleteById(id);
    }
}
