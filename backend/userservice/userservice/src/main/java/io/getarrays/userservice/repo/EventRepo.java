package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.Sport;
import io.getarrays.userservice.domain.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepo extends JpaRepository<SportEvent, UUID> {
    SportEvent findByName(String name);


    @Modifying
    @Query(value="DELETE FROM sport_sport_events s WHERE s.sport_events_id=:id", nativeQuery=true)
    void deleteEventLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM app_user_events s WHERE s.events_id=:id", nativeQuery=true)
    void deleteEventUsersLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM app_user_interests s WHERE s.interests_id=:id", nativeQuery=true)
    void deleteEventInterestsLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM sport_event_users s WHERE s.sport_event_id=:id", nativeQuery=true)
    void deleteEventOrganiserLegs(@Param("id") UUID id);

    @Query(value="SELECT * FROM sport_event ORDER BY name", nativeQuery = true)
    List<SportEvent> findEventsNameSort();

    @Query(value="SELECT * " + "FROM sport_event " +  "ORDER BY name DESC", nativeQuery = true)
    List<SportEvent> findEventsNameSortDesc();

    @Query(value="SELECT * FROM sport_event ORDER BY price", nativeQuery = true)
    List<SportEvent> findEventsPriceSort();

    @Query(value="SELECT * " + "FROM sport_event " +  "ORDER BY price DESC", nativeQuery = true)
    List<SportEvent> findEventsPriceSortDesc();

}
