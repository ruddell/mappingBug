package io.github.jhipster.mappingbug.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

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

    @ManyToMany
    @JoinTable(name = "campaign_beacons",
               joinColumns = @JoinColumn(name="campaigns_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="beacons_id", referencedColumnName="ID"))
    private Set<Beacon> beacons = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "campaign_applications",
               joinColumns = @JoinColumn(name="campaigns_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="applications_id", referencedColumnName="ID"))
    private Set<Application> applications = new HashSet<>();

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

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
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
        Campaign campaign = (Campaign) o;
        if(campaign.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, campaign.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
