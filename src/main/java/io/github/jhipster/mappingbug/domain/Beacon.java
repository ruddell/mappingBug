package io.github.jhipster.mappingbug.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Beacon.
 */
@Entity
@Table(name = "beacon")
public class Beacon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne
    private Company owner;

    @ManyToMany(mappedBy = "beacons")
    @JsonIgnore
    private Set<Campaign> campaigns = new HashSet<>();

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

    public Company getOwner() {
        return owner;
    }

    public void setOwner(Company company) {
        this.owner = company;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Beacon beacon = (Beacon) o;
        if(beacon.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, beacon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Beacon{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
