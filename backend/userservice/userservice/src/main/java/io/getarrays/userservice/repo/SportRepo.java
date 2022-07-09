package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SportRepo extends JpaRepository<Sport, UUID> {
    Sport findByName(String name);

    @Modifying
    @Query(value="DELETE FROM sport_sport_events s WHERE s.sport_id=:id", nativeQuery = true)
    void deleteSportLegs(@Param("id") UUID id);

    @Query(value="SELECT * FROM sport ORDER BY name", nativeQuery = true)
    List<Sport> findSportsNameSort();

    @Query(value="SELECT * " + "FROM sport " +  "ORDER BY name DESC", nativeQuery = true)
    List<Sport> findSportsNameSortDesc();

}
