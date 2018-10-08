package org.cheekylogic.whipround.repository;

import org.cheekylogic.whipround.domain.Preapproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Preapproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreapprovalRepository extends JpaRepository<Preapproval, Long> {

}
