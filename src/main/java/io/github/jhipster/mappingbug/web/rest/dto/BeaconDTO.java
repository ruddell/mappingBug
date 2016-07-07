package io.github.jhipster.mappingbug.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Beacon entity.
 */
public class BeaconDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String name;


    private Long ownerId;
    

    private String ownerName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeaconDTO beaconDTO = (BeaconDTO) o;

        if ( ! Objects.equals(id, beaconDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BeaconDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
