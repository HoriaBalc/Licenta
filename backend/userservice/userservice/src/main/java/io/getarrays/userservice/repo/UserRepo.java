package io.getarrays.userservice.repo;

import io.getarrays.userservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserRepo extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);

    @Modifying
    @Query(value="DELETE FROM app_user_events s WHERE s.app_user_id=:id", nativeQuery=true)
    void deleteUserEventsLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM app_user_interests s WHERE s.app_user_id=:id", nativeQuery=true)
    void deleteUserInterestsLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM sport_event_users s WHERE s.users_id=:id", nativeQuery=true)
    void deleteUserOrganiserLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM app_user_captain_teams s WHERE s.app_user_id=:id", nativeQuery=true)
    void deleteUserCaptainLegs(@Param("id") UUID id);

    @Modifying
    @Query(value="DELETE FROM team_teammates s WHERE s.teammates_id=:id", nativeQuery=true)
    void deleteUserTeammatesLegs(@Param("id") UUID id);

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY last_name", nativeQuery = true)
    List<AppUser> findUsersNameSort();

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY last_name DESC", nativeQuery = true)
    List<AppUser> findUsersNameSortDesc();

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY username", nativeQuery = true)
    List<AppUser> findUsersUsernameSort();

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY username DESC", nativeQuery = true)
    List<AppUser> findUsersUsernameSortDesc();

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY email", nativeQuery = true)
    List<AppUser> findUsersEmailSort();

    @Query(value="SELECT * " + "FROM app_user " +  "ORDER BY email DESC", nativeQuery = true)
    List<AppUser> findUsersEmailSortDesc();

   @Query(value="SELECT * " + "FROM app_user" +  "LIMIT (:page)*10 OFFSET (:page-1)*10", nativeQuery = true)
    //@Query(value="SELECT a FROM app_user a ORDER BY username OFFSET (:page-1)*10 ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
    List<AppUser> findUsersByPage(@Param("page") int page);
    int countAppUsersByUsernameNotNull();
}