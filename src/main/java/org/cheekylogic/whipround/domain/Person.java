package org.cheekylogic.whipround.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_contribution",
               joinColumns = @JoinColumn(name = "people_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "contributions_id", referencedColumnName = "id"))
    private Set<Contribution> contributions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "person_whipround",
               joinColumns = @JoinColumn(name = "people_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "whiprounds_id", referencedColumnName = "id"))
    private Set<Whipround> whiprounds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Person user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public Person contributions(Set<Contribution> contributions) {
        this.contributions = contributions;
        return this;
    }

    public Person addContribution(Contribution contribution) {
        this.contributions.add(contribution);
        contribution.getContributors().add(this);
        return this;
    }

    public Person removeContribution(Contribution contribution) {
        this.contributions.remove(contribution);
        contribution.getContributors().remove(this);
        return this;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<Whipround> getWhiprounds() {
        return whiprounds;
    }

    public Person whiprounds(Set<Whipround> whiprounds) {
        this.whiprounds = whiprounds;
        return this;
    }

    public Person addWhipround(Whipround whipround) {
        this.whiprounds.add(whipround);
        whipround.getOrganisers().add(this);
        return this;
    }

    public Person removeWhipround(Whipround whipround) {
        this.whiprounds.remove(whipround);
        whipround.getOrganisers().remove(this);
        return this;
    }

    public void setWhiprounds(Set<Whipround> whiprounds) {
        this.whiprounds = whiprounds;
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
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            "}";
    }
}
