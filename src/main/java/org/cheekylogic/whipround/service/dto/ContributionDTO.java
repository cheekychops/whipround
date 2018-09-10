package org.cheekylogic.whipround.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.cheekylogic.whipround.domain.enumeration.Currency;
import org.cheekylogic.whipround.domain.enumeration.ContributionStatus;

/**
 * A DTO for the Contribution entity.
 */
public class ContributionDTO implements Serializable {

    private Long id;

    private Currency currency;

    private BigDecimal amount;

    private ZonedDateTime date;

    private BigDecimal fee;

    private ContributionStatus status;

    private String payKey;

    private Long preapprovalId;

    private String preapprovalName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public ContributionStatus getStatus() {
        return status;
    }

    public void setStatus(ContributionStatus status) {
        this.status = status;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public Long getPreapprovalId() {
        return preapprovalId;
    }

    public void setPreapprovalId(Long preapprovalId) {
        this.preapprovalId = preapprovalId;
    }

    public String getPreapprovalName() {
        return preapprovalName;
    }

    public void setPreapprovalName(String preapprovalName) {
        this.preapprovalName = preapprovalName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContributionDTO contributionDTO = (ContributionDTO) o;
        if (contributionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contributionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContributionDTO{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", amount=" + getAmount() +
            ", date='" + getDate() + "'" +
            ", fee=" + getFee() +
            ", status='" + getStatus() + "'" +
            ", payKey='" + getPayKey() + "'" +
            ", preapproval=" + getPreapprovalId() +
            ", preapproval='" + getPreapprovalName() + "'" +
            "}";
    }
}
