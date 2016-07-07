package io.github.jhipster.mappingbug.web.rest.mapper;

import io.github.jhipster.mappingbug.domain.*;
import io.github.jhipster.mappingbug.web.rest.dto.CampaignDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Campaign and its DTO CampaignDTO.
 */
@Mapper(componentModel = "spring", uses = {BeaconMapper.class, ApplicationMapper.class, })
public interface CampaignMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    CampaignDTO campaignToCampaignDTO(Campaign campaign);

    List<CampaignDTO> campaignsToCampaignDTOs(List<Campaign> campaigns);

    @Mapping(source = "ownerId", target = "owner")
    Campaign campaignDTOToCampaign(CampaignDTO campaignDTO);

    List<Campaign> campaignDTOsToCampaigns(List<CampaignDTO> campaignDTOs);

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }

    default Beacon beaconFromId(Long id) {
        if (id == null) {
            return null;
        }
        Beacon beacon = new Beacon();
        beacon.setId(id);
        return beacon;
    }

    default Application applicationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Application application = new Application();
        application.setId(id);
        return application;
    }
}
