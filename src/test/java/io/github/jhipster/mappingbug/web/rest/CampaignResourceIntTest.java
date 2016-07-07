package io.github.jhipster.mappingbug.web.rest;

import io.github.jhipster.mappingbug.MappingBugApp;
import io.github.jhipster.mappingbug.domain.Campaign;
import io.github.jhipster.mappingbug.repository.CampaignRepository;
import io.github.jhipster.mappingbug.web.rest.dto.CampaignDTO;
import io.github.jhipster.mappingbug.web.rest.mapper.CampaignMapper;

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
 * Test class for the CampaignResource REST controller.
 *
 * @see CampaignResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MappingBugApp.class)
@WebAppConfiguration
@IntegrationTest
public class CampaignResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";

    @Inject
    private CampaignRepository campaignRepository;

    @Inject
    private CampaignMapper campaignMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCampaignMockMvc;

    private Campaign campaign;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CampaignResource campaignResource = new CampaignResource();
        ReflectionTestUtils.setField(campaignResource, "campaignRepository", campaignRepository);
        ReflectionTestUtils.setField(campaignResource, "campaignMapper", campaignMapper);
        this.restCampaignMockMvc = MockMvcBuilders.standaloneSetup(campaignResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        campaign = new Campaign();
        campaign.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCampaign() throws Exception {
        int databaseSizeBeforeCreate = campaignRepository.findAll().size();

        // Create the Campaign
        CampaignDTO campaignDTO = campaignMapper.campaignToCampaignDTO(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
                .andExpect(status().isCreated());

        // Validate the Campaign in the database
        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(databaseSizeBeforeCreate + 1);
        Campaign testCampaign = campaigns.get(campaigns.size() - 1);
        assertThat(testCampaign.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignRepository.findAll().size();
        // set the field null
        campaign.setName(null);

        // Create the Campaign, which fails.
        CampaignDTO campaignDTO = campaignMapper.campaignToCampaignDTO(campaign);

        restCampaignMockMvc.perform(post("/api/campaigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
                .andExpect(status().isBadRequest());

        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaigns() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get all the campaigns
        restCampaignMockMvc.perform(get("/api/campaigns?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(campaign.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);

        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", campaign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(campaign.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCampaign() throws Exception {
        // Get the campaign
        restCampaignMockMvc.perform(get("/api/campaigns/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);
        int databaseSizeBeforeUpdate = campaignRepository.findAll().size();

        // Update the campaign
        Campaign updatedCampaign = new Campaign();
        updatedCampaign.setId(campaign.getId());
        updatedCampaign.setName(UPDATED_NAME);
        CampaignDTO campaignDTO = campaignMapper.campaignToCampaignDTO(updatedCampaign);

        restCampaignMockMvc.perform(put("/api/campaigns")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(campaignDTO)))
                .andExpect(status().isOk());

        // Validate the Campaign in the database
        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(databaseSizeBeforeUpdate);
        Campaign testCampaign = campaigns.get(campaigns.size() - 1);
        assertThat(testCampaign.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCampaign() throws Exception {
        // Initialize the database
        campaignRepository.saveAndFlush(campaign);
        int databaseSizeBeforeDelete = campaignRepository.findAll().size();

        // Get the campaign
        restCampaignMockMvc.perform(delete("/api/campaigns/{id}", campaign.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Campaign> campaigns = campaignRepository.findAll();
        assertThat(campaigns).hasSize(databaseSizeBeforeDelete - 1);
    }
}
