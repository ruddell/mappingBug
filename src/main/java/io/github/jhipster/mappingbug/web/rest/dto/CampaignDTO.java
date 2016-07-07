package io.github.jhipster.mappingbug.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Campaign entity.
 */
public class CampaignDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;


    private Long ownerId;
    

    private String ownerName;

    private Set<BeaconDTO> beacons = new HashSet<>();

    private Set<ApplicationDTO> applications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long companyId) {
        this.ownerId = companyId;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String companyName) {
        this.ownerName = companyName;
    }

    public Set<BeaconDTO> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<BeaconDTO> beacons) {
        this.beacons = beacons;
    }

    public Set<ApplicationDTO> getApplications() {
        return applications;
    }

    public void setApplications(Set<ApplicationDTO> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CampaignDTO campaignDTO = (CampaignDTO) o;

        if ( ! Objects.equals(id, campaignDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CampaignDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
