package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TeamRepo extends JpaRepository<Team, UUID> {

    @Modifying
    @Query(value="DELETE FROM app_user_captain_teams s WHERE s.captain_teams_id=:id", nativeQuery=true)
    void deleteTeamCaptainLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM team_teammates s WHERE s.team_id=:id", nativeQuery=true)
    void deleteTeamTeammatesLegs(@Param("id") UUID id);

    Team findByName(String name);
    Team findByCode(String code);
}
