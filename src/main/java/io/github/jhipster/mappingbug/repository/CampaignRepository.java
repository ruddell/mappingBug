package io.github.jhipster.mappingbug.repository;

import io.github.jhipster.mappingbug.domain.Campaign;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Campaign entity.
 */
@SuppressWarnings("unused")
public interface CampaignRepository extends JpaRepository<Campaign,Long> {

    @Query("select distinct campaign from Campaign campaign left join fetch campaign.beacons left join fetch campaign.applications")
    List<Campaign> findAllWithEagerRelationships();

    @Query("select campaign from Campaign campaign left join fetch campaign.beacons left join fetch campaign.applications where campaign.id =:id")
    Campaign findOneWithEagerRelationships(@Param("id") Long id);

}
