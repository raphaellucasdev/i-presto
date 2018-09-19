package br.com.presto.repository;

import br.com.presto.domain.Prestador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Prestador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {

}
