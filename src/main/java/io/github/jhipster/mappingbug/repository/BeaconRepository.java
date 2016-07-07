package io.github.jhipster.mappingbug.repository;

import io.github.jhipster.mappingbug.domain.Beacon;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Beacon entity.
 */
@SuppressWarnings("unused")
public interface BeaconRepository extends JpaRepository<Beacon,Long> {

}
