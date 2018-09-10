package org.cheekylogic.whipround.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Preapproval.
 */
@Entity
@Table(name = "preapproval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Preapproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "preapproval")
    @JsonIgnore
    private Contribution contribution;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Preapproval name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contribution getContribution() {
        return contribution;
    }

    public Preapproval contribution(Contribution contribution) {
        this.contribution = contribution;
        return this;
    }

    public void setContribution(Contribution contribution) {
        this.contribution = contribution;
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
        Preapproval preapproval = (Preapproval) o;
        if (preapproval.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preapproval.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Preapproval{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
