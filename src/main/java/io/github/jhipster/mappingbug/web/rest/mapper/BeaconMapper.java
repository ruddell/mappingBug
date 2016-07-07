package io.github.jhipster.mappingbug.web.rest.mapper;

import io.github.jhipster.mappingbug.domain.*;
import io.github.jhipster.mappingbug.web.rest.dto.BeaconDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Beacon and its DTO BeaconDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BeaconMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    BeaconDTO beaconToBeaconDTO(Beacon beacon);

    List<BeaconDTO> beaconsToBeaconDTOs(List<Beacon> beacons);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "campaigns", ignore = true)
    Beacon beaconDTOToBeacon(BeaconDTO beaconDTO);

    List<Beacon> beaconDTOsToBeacons(List<BeaconDTO> beaconDTOs);

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
