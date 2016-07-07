package io.github.jhipster.mappingbug.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.mappingbug.domain.Application;
import io.github.jhipster.mappingbug.repository.ApplicationRepository;
import io.github.jhipster.mappingbug.web.rest.util.HeaderUtil;
import io.github.jhipster.mappingbug.web.rest.dto.ApplicationDTO;
import io.github.jhipster.mappingbug.web.rest.mapper.ApplicationMapper;
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
 * REST controller for managing Application.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);
        
    @Inject
    private ApplicationRepository applicationRepository;
    
    @Inject
    private ApplicationMapper applicationMapper;
    
    /**
     * POST  /applications : Create a new application.
     *
     * @param applicationDTO the applicationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationDTO, or with status 400 (Bad Request) if the application has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/applications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
        log.debug("REST request to save Application : {}", applicationDTO);
        if (applicationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("application", "idexists", "A new application cannot already have an ID")).body(null);
        }
        Application application = applicationMapper.applicationDTOToApplication(applicationDTO);
        application = applicationRepository.save(application);
        ApplicationDTO result = applicationMapper.applicationToApplicationDTO(application);
        return ResponseEntity.created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("application", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applications : Updates an existing application.
     *
     * @param applicationDTO the applicationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationDTO,
     * or with status 400 (Bad Request) if the applicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/applications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationDTO> updateApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
        log.debug("REST request to update Application : {}", applicationDTO);
        if (applicationDTO.getId() == null) {
            return createApplication(applicationDTO);
        }
        Application application = applicationMapper.applicationDTOToApplication(applicationDTO);
        application = applicationRepository.save(application);
        ApplicationDTO result = applicationMapper.applicationToApplicationDTO(application);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("application", applicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applications : get all the applications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applications in body
     */
    @RequestMapping(value = "/applications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ApplicationDTO> getAllApplications() {
        log.debug("REST request to get all Applications");
        List<Application> applications = applicationRepository.findAll();
        return applicationMapper.applicationsToApplicationDTOs(applications);
    }

    /**
     * GET  /applications/:id : get the "id" application.
     *
     * @param id the id of the applicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/applications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Application application = applicationRepository.findOne(id);
        ApplicationDTO applicationDTO = applicationMapper.applicationToApplicationDTO(application);
        return Optional.ofNullable(applicationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /applications/:id : delete the "id" application.
     *
     * @param id the id of the applicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/applications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("application", id.toString())).build();
    }

}
