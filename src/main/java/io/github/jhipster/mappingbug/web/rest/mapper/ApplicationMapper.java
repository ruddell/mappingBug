package io.github.jhipster.mappingbug.web.rest.mapper;

import io.github.jhipster.mappingbug.domain.*;
import io.github.jhipster.mappingbug.web.rest.dto.ApplicationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Application and its DTO ApplicationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    ApplicationDTO applicationToApplicationDTO(Application application);

    List<ApplicationDTO> applicationsToApplicationDTOs(List<Application> applications);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "campaigns", ignore = true)
    Application applicationDTOToApplication(ApplicationDTO applicationDTO);

    List<Application> applicationDTOsToApplications(List<ApplicationDTO> applicationDTOs);

    default Company companyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
