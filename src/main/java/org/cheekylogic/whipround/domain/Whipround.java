package org.cheekylogic.whipround.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.cheekylogic.whipround.domain.enumeration.WhiproundStatus;

/**
 * A Whipround.
 */
@Entity
@Table(name = "whipround")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Whipround implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invitation")
    private String invitation;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WhiproundStatus status;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "whipround_contribution",
               joinColumns = @JoinColumn(name = "whiprounds_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "contributions_id", referencedColumnName = "id"))
    private Set<Contribution> contributions = new HashSet<>();

    @ManyToMany(mappedBy = "whiprounds")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> organisers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitation() {
        return invitation;
    }

    public Whipround invitation(String invitation) {
        this.invitation = invitation;
        return this;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Whipround imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Whipround startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Whipround endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public WhiproundStatus getStatus() {
        return status;
    }

    public Whipround status(WhiproundStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(WhiproundStatus status) {
        this.status = status;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public Whipround contributions(Set<Contribution> contributions) {
        this.contributions = contributions;
        return this;
    }

    public Whipround addContribution(Contribution contribution) {
        this.contributions.add(contribution);
        contribution.getWhiprounds().add(this);
        return this;
    }

    public Whipround removeContribution(Contribution contribution) {
        this.contributions.remove(contribution);
        contribution.getWhiprounds().remove(this);
        return this;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<Person> getOrganisers() {
        return organisers;
    }

    public Whipround organisers(Set<Person> people) {
        this.organisers = people;
        return this;
    }

    public Whipround addOrganiser(Person person) {
        this.organisers.add(person);
        person.getWhiprounds().add(this);
        return this;
    }

    public Whipround removeOrganiser(Person person) {
        this.organisers.remove(person);
        person.getWhiprounds().remove(this);
        return this;
    }

    public void setOrganisers(Set<Person> people) {
        this.organisers = people;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Whipround whipround = (Whipround) o;
        if (whipround.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), whipround.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Whipround{" +
            "id=" + getId() +
            ", invitation='" + getInvitation() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
