package io.github.jhipster.mappingbug.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.mappingbug.domain.Company;
import io.github.jhipster.mappingbug.repository.CompanyRepository;
import io.github.jhipster.mappingbug.web.rest.util.HeaderUtil;
import io.github.jhipster.mappingbug.web.rest.dto.CompanyDTO;
import io.github.jhipster.mappingbug.web.rest.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class CompanyResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);
        
    @Inject
    private CompanyRepository companyRepository;
    
    @Inject
    private CompanyMapper companyMapper;
    
    /**
     * POST  /companies : Create a new company.
     *
     * @param companyDTO the companyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDTO, or with status 400 (Bad Request) if the company has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/companies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to save Company : {}", companyDTO);
        if (companyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("company", "idexists", "A new company cannot already have an ID")).body(null);
        }
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.companyToCompanyDTO(company);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("company", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /companies : Updates an existing company.
     *
     * @param companyDTO the companyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDTO,
     * or with status 400 (Bad Request) if the companyDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/companies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyDTO> updateCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        log.debug("REST request to update Company : {}", companyDTO);
        if (companyDTO.getId() == null) {
            return createCompany(companyDTO);
        }
        Company company = companyMapper.companyDTOToCompany(companyDTO);
        company = companyRepository.save(company);
        CompanyDTO result = companyMapper.companyToCompanyDTO(company);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("company", companyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /companies : get all the companies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companies in body
     */
    @RequestMapping(value = "/companies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CompanyDTO> getAllCompanies() {
        log.debug("REST request to get all Companies");
        List<Company> companies = companyRepository.findAllWithEagerRelationships();
        return companyMapper.companiesToCompanyDTOs(companies);
    }

    /**
     * GET  /companies/:id : get the "id" company.
     *
     * @param id the id of the companyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/companies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        log.debug("REST request to get Company : {}", id);
        Company company = companyRepository.findOneWithEagerRelationships(id);
        CompanyDTO companyDTO = companyMapper.companyToCompanyDTO(company);
        return Optional.ofNullable(companyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /companies/:id : delete the "id" company.
     *
     * @param id the id of the companyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/companies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("company", id.toString())).build();
    }

}
