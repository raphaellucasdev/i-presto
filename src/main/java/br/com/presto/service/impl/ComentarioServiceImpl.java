package br.com.presto.service.impl;

import br.com.presto.service.ComentarioService;
import br.com.presto.domain.Comentario;
import br.com.presto.repository.ComentarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Comentario.
 */
@Service
@Transactional
public class ComentarioServiceImpl implements ComentarioService {

    private final Logger log = LoggerFactory.getLogger(ComentarioServiceImpl.class);

    private final ComentarioRepository comentarioRepository;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    /**
     * Save a comentario.
     *
     * @param comentario the entity to save
     * @return the persisted entity
     */
    @Override
    public Comentario save(Comentario comentario) {
        log.debug("Request to save Comentario : {}", comentario);        return comentarioRepository.save(comentario);
    }

    /**
     * Get all the comentarios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comentario> findAll() {
        log.debug("Request to get all Comentarios");
        return comentarioRepository.findAll();
    }


    /**
     * Get one comentario by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Comentario> findOne(Long id) {
        log.debug("Request to get Comentario : {}", id);
        return comentarioRepository.findById(id);
    }

    /**
     * Delete the comentario by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comentario : {}", id);
        comentarioRepository.deleteById(id);
    }
}
