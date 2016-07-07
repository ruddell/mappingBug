package io.github.jhipster.mappingbug.web.rest;

import io.github.jhipster.mappingbug.MappingBugApp;
import io.github.jhipster.mappingbug.domain.Beacon;
import io.github.jhipster.mappingbug.repository.BeaconRepository;
import io.github.jhipster.mappingbug.web.rest.dto.BeaconDTO;
import io.github.jhipster.mappingbug.web.rest.mapper.BeaconMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BeaconResource REST controller.
 *
 * @see BeaconResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MappingBugApp.class)
@WebAppConfiguration
@IntegrationTest
public class BeaconResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";

    @Inject
    private BeaconRepository beaconRepository;

    @Inject
    private BeaconMapper beaconMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBeaconMockMvc;

    private Beacon beacon;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BeaconResource beaconResource = new BeaconResource();
        ReflectionTestUtils.setField(beaconResource, "beaconRepository", beaconRepository);
        ReflectionTestUtils.setField(beaconResource, "beaconMapper", beaconMapper);
        this.restBeaconMockMvc = MockMvcBuilders.standaloneSetup(beaconResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        beacon = new Beacon();
        beacon.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBeacon() throws Exception {
        int databaseSizeBeforeCreate = beaconRepository.findAll().size();

        // Create the Beacon
        BeaconDTO beaconDTO = beaconMapper.beaconToBeaconDTO(beacon);

        restBeaconMockMvc.perform(post("/api/beacons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beaconDTO)))
                .andExpect(status().isCreated());

        // Validate the Beacon in the database
        List<Beacon> beacons = beaconRepository.findAll();
        assertThat(beacons).hasSize(databaseSizeBeforeCreate + 1);
        Beacon testBeacon = beacons.get(beacons.size() - 1);
        assertThat(testBeacon.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beaconRepository.findAll().size();
        // set the field null
        beacon.setName(null);

        // Create the Beacon, which fails.
        BeaconDTO beaconDTO = beaconMapper.beaconToBeaconDTO(beacon);

        restBeaconMockMvc.perform(post("/api/beacons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beaconDTO)))
                .andExpect(status().isBadRequest());

        List<Beacon> beacons = beaconRepository.findAll();
        assertThat(beacons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeacons() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        // Get all the beacons
        restBeaconMockMvc.perform(get("/api/beacons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(beacon.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", beacon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(beacon.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBeacon() throws Exception {
        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);
        int databaseSizeBeforeUpdate = beaconRepository.findAll().size();

        // Update the beacon
        Beacon updatedBeacon = new Beacon();
        updatedBeacon.setId(beacon.getId());
        updatedBeacon.setName(UPDATED_NAME);
        BeaconDTO beaconDTO = beaconMapper.beaconToBeaconDTO(updatedBeacon);

        restBeaconMockMvc.perform(put("/api/beacons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(beaconDTO)))
                .andExpect(status().isOk());

        // Validate the Beacon in the database
        List<Beacon> beacons = beaconRepository.findAll();
        assertThat(beacons).hasSize(databaseSizeBeforeUpdate);
        Beacon testBeacon = beacons.get(beacons.size() - 1);
        assertThat(testBeacon.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);
        int databaseSizeBeforeDelete = beaconRepository.findAll().size();

        // Get the beacon
        restBeaconMockMvc.perform(delete("/api/beacons/{id}", beacon.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Beacon> beacons = beaconRepository.findAll();
        assertThat(beacons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
