package io.getarrays.userservice.service;

//import io.getarrays.userservice.api.UserToEventForm;
import io.getarrays.userservice.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface UserService {
    AppUser saveUser(AppUser user);
    Message saveMessage(Message message);
    Role saveRole(Role role);
    Sport saveSport(Sport sport);
    Team saveTeam(Team team);
    SportEvent saveEvent(SportEvent event);
    void addRoleToUser(String username, String roleName);
    void addCaptainTeamToUser(String username, String teamName);
    void addUserToTeam(String username, String teamName);
    void addEventToTeam(String teamName, String eventName);
    void addUserToEvent(String username, String eventName);
    void addEventToSport(String eventName, String sportName);
    void changeRoleToUser(String username, String roleName);
   // void addTeamToEvent(String teamName, String eventName);
    void addEventToUser(String username, String eventName);
    void addEventInterestToUser(String username, String eventName);
    AppUser getUser(String username);
    AppUser getUserByEmail(String email);
    Role getRole(String name);
    Sport getSport(String name);
    SportEvent getEvent(String name);
    Team getTeam(String name);
    List<AppUser> getUsersByPage(int page);
    List<SportEvent> getEvents();
    List<Message> getMessages();
    List<Sport> getSports();
    List<Team> getTeams();
    List<AppUser> getUsers();
    List<AppUser> getUsersSortName();
    List<AppUser> getUsersSortNameDesc();
    List<Sport> getSportsSortName();
    List<Sport> getSportsSortNameDesc();
    List<AppUser> getUsersSortUsername();
    List<AppUser> getUsersSortUsernameDesc();
    List<AppUser> getUsersSortEmail();
    List<AppUser> getUsersSortEmailDesc();
    List<SportEvent> getEventsSortName();
    List<SportEvent> getEventsSortNameDesc();
    List<SportEvent> getEventsSortPrice();
    List<SportEvent> getEventsSortPriceDesc();
    List<Role> getRoles();
    UUID deleteUserById(UUID id);
    UUID deleteRoleById(UUID id);
    UUID deleteTeamById(UUID id);
    UUID deleteEventById(UUID id);
    UUID deleteSportById(UUID id);
    UUID deleteMessageById(UUID id);
    void deleteSportLegs(UUID id);
    void deleteEventLegs(UUID id);
    void deleteEventUsersLegs(UUID id);
    void deleteEventInterestsLegs(UUID id);
    void deleteEventOrganiserLegs(UUID id);
    void deleteUserEventsLegs(UUID id);
    void deleteUserInterestsLegs(UUID id);
    void deleteUserOrganiserLegs(UUID id);
    void deleteUserCaptainLegs(UUID id);
    void deleteUserTeammatesLegs(UUID id);
    void deleteEventInterestToUser(String username, String eventName);
    void deleteTeamCaptainLegs(UUID id);
    void deleteTeamTeammatesLegs(UUID id);


}
