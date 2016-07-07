package io.github.jhipster.mappingbug.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.mappingbug.domain.Beacon;
import io.github.jhipster.mappingbug.repository.BeaconRepository;
import io.github.jhipster.mappingbug.web.rest.util.HeaderUtil;
import io.github.jhipster.mappingbug.web.rest.dto.BeaconDTO;
import io.github.jhipster.mappingbug.web.rest.mapper.BeaconMapper;
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
 * REST controller for managing Beacon.
 */
@RestController
@RequestMapping("/api")
public class BeaconResource {

    private final Logger log = LoggerFactory.getLogger(BeaconResource.class);
        
    @Inject
    private BeaconRepository beaconRepository;
    
    @Inject
    private BeaconMapper beaconMapper;
    
    /**
     * POST  /beacons : Create a new beacon.
     *
     * @param beaconDTO the beaconDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new beaconDTO, or with status 400 (Bad Request) if the beacon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beacons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeaconDTO> createBeacon(@Valid @RequestBody BeaconDTO beaconDTO) throws URISyntaxException {
        log.debug("REST request to save Beacon : {}", beaconDTO);
        if (beaconDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("beacon", "idexists", "A new beacon cannot already have an ID")).body(null);
        }
        Beacon beacon = beaconMapper.beaconDTOToBeacon(beaconDTO);
        beacon = beaconRepository.save(beacon);
        BeaconDTO result = beaconMapper.beaconToBeaconDTO(beacon);
        return ResponseEntity.created(new URI("/api/beacons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("beacon", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /beacons : Updates an existing beacon.
     *
     * @param beaconDTO the beaconDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beaconDTO,
     * or with status 400 (Bad Request) if the beaconDTO is not valid,
     * or with status 500 (Internal Server Error) if the beaconDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/beacons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeaconDTO> updateBeacon(@Valid @RequestBody BeaconDTO beaconDTO) throws URISyntaxException {
        log.debug("REST request to update Beacon : {}", beaconDTO);
        if (beaconDTO.getId() == null) {
            return createBeacon(beaconDTO);
        }
        Beacon beacon = beaconMapper.beaconDTOToBeacon(beaconDTO);
        beacon = beaconRepository.save(beacon);
        BeaconDTO result = beaconMapper.beaconToBeaconDTO(beacon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("beacon", beaconDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /beacons : get all the beacons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of beacons in body
     */
    @RequestMapping(value = "/beacons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BeaconDTO> getAllBeacons() {
        log.debug("REST request to get all Beacons");
        List<Beacon> beacons = beaconRepository.findAll();
        return beaconMapper.beaconsToBeaconDTOs(beacons);
    }

    /**
     * GET  /beacons/:id : get the "id" beacon.
     *
     * @param id the id of the beaconDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the beaconDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/beacons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BeaconDTO> getBeacon(@PathVariable Long id) {
        log.debug("REST request to get Beacon : {}", id);
        Beacon beacon = beaconRepository.findOne(id);
        BeaconDTO beaconDTO = beaconMapper.beaconToBeaconDTO(beacon);
        return Optional.ofNullable(beaconDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /beacons/:id : delete the "id" beacon.
     *
     * @param id the id of the beaconDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/beacons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBeacon(@PathVariable Long id) {
        log.debug("REST request to delete Beacon : {}", id);
        beaconRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("beacon", id.toString())).build();
    }

}
