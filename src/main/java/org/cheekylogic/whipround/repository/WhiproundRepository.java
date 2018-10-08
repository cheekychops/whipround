package org.cheekylogic.whipround.repository;

import org.cheekylogic.whipround.domain.Whipround;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Whipround entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WhiproundRepository extends JpaRepository<Whipround, Long> {

    @Query(value = "select distinct whipround from Whipround whipround left join fetch whipround.contributions",
        countQuery = "select count(distinct whipround) from Whipround whipround")
    Page<Whipround> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct whipround from Whipround whipround left join fetch whipround.contributions")
    List<Whipround> findAllWithEagerRelationships();

    @Query("select whipround from Whipround whipround left join fetch whipround.contributions where whipround.id =:id")
    Optional<Whipround> findOneWithEagerRelationships(@Param("id") Long id);

}
