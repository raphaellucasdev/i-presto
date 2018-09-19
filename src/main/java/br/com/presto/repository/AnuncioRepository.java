package br.com.presto.repository;

import br.com.presto.domain.Anuncio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Anuncio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

}
