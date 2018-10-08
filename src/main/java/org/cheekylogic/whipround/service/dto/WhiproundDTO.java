package org.cheekylogic.whipround.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import org.cheekylogic.whipround.domain.enumeration.WhiproundStatus;

/**
 * A DTO for the Whipround entity.
 */
public class WhiproundDTO implements Serializable {

    private Long id;

    private String invitation;

    private String imageUrl;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private WhiproundStatus status;

    private Set<ContributionDTO> contributions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public WhiproundStatus getStatus() {
        return status;
    }

    public void setStatus(WhiproundStatus status) {
        this.status = status;
    }

    public Set<ContributionDTO> getContributions() {
        return contributions;
    }

    public void setContributions(Set<ContributionDTO> contributions) {
        this.contributions = contributions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WhiproundDTO whiproundDTO = (WhiproundDTO) o;
        if (whiproundDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), whiproundDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WhiproundDTO{" +
            "id=" + getId() +
            ", invitation='" + getInvitation() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
