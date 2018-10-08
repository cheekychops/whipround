package org.cheekylogic.whipround.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.cheekylogic.whipround.domain.enumeration.Currency;

import org.cheekylogic.whipround.domain.enumeration.ContributionStatus;

/**
 * A Contribution.
 */
@Entity
@Table(name = "contribution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "fee", precision = 10, scale = 2)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContributionStatus status;

    @Column(name = "pay_key")
    private String payKey;

    @OneToOne
    @JoinColumn(unique = true)
    private Preapproval preapproval;

    @ManyToMany(mappedBy = "contributions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Whipround> whiprounds = new HashSet<>();

    @ManyToMany(mappedBy = "contributions")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Person> contributors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Contribution currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Contribution amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Contribution date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public Contribution fee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public ContributionStatus getStatus() {
        return status;
    }

    public Contribution status(ContributionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ContributionStatus status) {
        this.status = status;
    }

    public String getPayKey() {
        return payKey;
    }

    public Contribution payKey(String payKey) {
        this.payKey = payKey;
        return this;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public Preapproval getPreapproval() {
        return preapproval;
    }

    public Contribution preapproval(Preapproval preapproval) {
        this.preapproval = preapproval;
        return this;
    }

    public void setPreapproval(Preapproval preapproval) {
        this.preapproval = preapproval;
    }

    public Set<Whipround> getWhiprounds() {
        return whiprounds;
    }

    public Contribution whiprounds(Set<Whipround> whiprounds) {
        this.whiprounds = whiprounds;
        return this;
    }

    public Contribution addWhipround(Whipround whipround) {
        this.whiprounds.add(whipround);
        whipround.getContributions().add(this);
        return this;
    }

    public Contribution removeWhipround(Whipround whipround) {
        this.whiprounds.remove(whipround);
        whipround.getContributions().remove(this);
        return this;
    }

    public void setWhiprounds(Set<Whipround> whiprounds) {
        this.whiprounds = whiprounds;
    }

    public Set<Person> getContributors() {
        return contributors;
    }

    public Contribution contributors(Set<Person> people) {
        this.contributors = people;
        return this;
    }

    public Contribution addContributor(Person person) {
        this.contributors.add(person);
        person.getContributions().add(this);
        return this;
    }

    public Contribution removeContributor(Person person) {
        this.contributors.remove(person);
        person.getContributions().remove(this);
        return this;
    }

    public void setContributors(Set<Person> people) {
        this.contributors = people;
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
        Contribution contribution = (Contribution) o;
        if (contribution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contribution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contribution{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            ", fee=" + getFee() +
            ", status='" + getStatus() + "'" +
            ", payKey='" + getPayKey() + "'" +
            "}";
    }
}
