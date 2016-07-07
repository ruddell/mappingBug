package io.github.jhipster.mappingbug.repository;

import io.github.jhipster.mappingbug.domain.Company;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Company entity.
 */
@SuppressWarnings("unused")
public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select distinct company from Company company left join fetch company.users")
    List<Company> findAllWithEagerRelationships();

    @Query("select company from Company company left join fetch company.users where company.id =:id")
    Company findOneWithEagerRelationships(@Param("id") Long id);

}
