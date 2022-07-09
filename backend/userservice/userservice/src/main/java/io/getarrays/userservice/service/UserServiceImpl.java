package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.*;
import io.getarrays.userservice.domain.SportEvent;

import io.getarrays.userservice.repo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements  UserService, UserDetailsService {
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RoleRepo roleRepo;
    @Autowired
    private final SportRepo sportRepo;
    @Autowired
    private final EventRepo eventRepo;
    @Autowired
    private final TeamRepo teamRepo;
    @Autowired
    private final MessageRepo messageRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if(user==null){
            log.error("User not found in the database");
            throw  new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role-> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(!user.isEnabled())
        EmailServiceImpl.sendRegisterEmail(user);
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return roleRepo.save(role);
    }


    @Override
    public Message saveMessage(Message message) {
        log.info("Saving new message {} to database", message.getMessage());
        return messageRepo.save(message);
    }

    @Override
    public SportEvent saveEvent(SportEvent event) {
        log.info("Saving new event {} to database", event.getName());
        return eventRepo.save(event);
    }

    @Override
    public Sport saveSport(Sport sport) {
        log.info("Saving new sport {} to database", sport.getName());
        return sportRepo.save(sport);
    }

    @Override
    public Team saveTeam(Team team) {
        log.info("Saving new team {} to database", team.getName());
        return teamRepo.save(team);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {} to database", username, roleName);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void addCaptainTeamToUser(String username, String teamName) {
        log.info("Adding user {} to captainTeams {} to database", username, teamName);
        AppUser user = userRepo.findByUsername(username);
        Team team = teamRepo.findByName(teamName);
        user.getCaptainTeams().add(team);
    }

    @Override
    public void addUserToTeam(String username, String teamName) {
        log.info("Adding team {} to user {} to database", username, teamName);
        AppUser user = userRepo.findByUsername(username);
        Team team = teamRepo.findByName(teamName);
        team.getTeammates().add(user);
    }

//    @Override
//    public void addTeamToEvent(String teamName, String eventName){
//        log.info("Adding team {} to event {} to database", eventName, teamName);
//        SportEvent event = eventRepo.findByName(eventName);
//        Team team = teamRepo.findByName(teamName);
//        event.getTeamList().add(team);
//    }

    @Override
    public void addEventToTeam(String teamName, String eventName) {
        log.info("Adding event {} to team {} to database", teamName, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        Team team = teamRepo.findByName(teamName);
        team.setSportEvent(event);
    }

    @Override
    public void addUserToEvent(String username, String eventName) {
        log.info("Adding user {} to event {} to database", username, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        AppUser user = userRepo.findByUsername(username);
        event.getUsers().add(user);
    }

    @Override
    public void addEventToUser(String username, String eventName) {
        log.info("Adding user {} to event {} to database", username, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        AppUser user = userRepo.findByUsername(username);
        user.getEvents().add(event);
    }

    @Override
    public void addEventInterestToUser(String username, String eventName) {
        log.info("Adding user {} to event {} to database", username, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        AppUser user = userRepo.findByUsername(username);
        user.getInterests().add(event);
    }

    @Override
    public void deleteEventInterestToUser(String username, String eventName) {
        log.info("Deleting user {} to event {} to database", username, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        AppUser user = userRepo.findByUsername(username);
        user.getInterests().remove(event);
        System.out.println(user.getInterests());
    }

    @Override
    public void changeRoleToUser(String username, String roleName){
        log.info("Changing role {} to user {} to database", username, roleName);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.setRoles(new ArrayList<Role>());
        user.getRoles().add(role);
    }

    @Override
    public void addEventToSport(String eventName, String sportName) {
        log.info("Adding event {} to sport {} to database", sportName, eventName);
        SportEvent event = eventRepo.findByName(eventName);
        Sport sport = sportRepo.findByName(sportName);
        sport.getSportEvents().add(event);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        log.info("Fetching user {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public Role getRole(String name) {
        log.info("Fetching role {}", name);
        return roleRepo.findByName(name);
    }

    @Override
    public Sport getSport(String name){
        log.info("Fetching sport {}", name);
        return sportRepo.findByName(name);
    }

    @Override
    public Team getTeam(String name){
        log.info("Fetching sport {}", name);
        return teamRepo.findByName(name);
    }

    @Override
    public SportEvent getEvent(String name){
        log.info("Fetching sport {}", name);
        return eventRepo.findByName(name);
    }

    @Override
    public List<AppUser> getUsersByPage(int page) {
        log.info("Fetching users by " + page);
        return userRepo.findUsersByPage(page);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users ");
        return userRepo.findAll();
    }

    @Override
    public List<Sport> getSportsSortName() {
        log.info("Fetching all users sorting by name");
        return sportRepo.findSportsNameSort();
    }

    @Override
    public List<Sport> getSportsSortNameDesc() {
        log.info("Fetching all users sorting by name");
        return sportRepo.findSportsNameSortDesc();
    }

    @Override
    public List<SportEvent> getEventsSortNameDesc() {
        log.info("Fetching all users sorting by name");
        return eventRepo.findEventsNameSortDesc();
    }

    @Override
    public List<SportEvent> getEventsSortName() {
        log.info("Fetching all users sorting by name");
        return eventRepo.findEventsNameSort();
    }

    @Override
    public List<SportEvent> getEventsSortPriceDesc() {
        log.info("Fetching all users sorting by name");
        return eventRepo.findEventsPriceSortDesc();
    }

    @Override
    public List<SportEvent> getEventsSortPrice() {
        log.info("Fetching all users sorting by name");
        return eventRepo.findEventsPriceSort();
    }

    @Override
    public List<AppUser> getUsersSortName() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersNameSort();
    }
    @Override
    public List<AppUser> getUsersSortNameDesc() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersNameSortDesc();
    }

    @Override
    public List<AppUser> getUsersSortUsername() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersUsernameSort();
    }

    @Override
    public List<AppUser> getUsersSortUsernameDesc() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersUsernameSortDesc();
    }

    @Override
    public List<AppUser> getUsersSortEmail() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersEmailSort();
    }

    @Override
    public List<AppUser> getUsersSortEmailDesc() {
        log.info("Fetching all users sorting by name");
        return userRepo.findUsersEmailSortDesc();
    }


    @Override
    public List<Role> getRoles() {
        log.info("Fetching all users ");
        return roleRepo.findAll();
    }

    @Override
    public List<SportEvent> getEvents() {
        log.info("Fetching all users ");
        return eventRepo.findAll();
    }

    @Override
    public List<Message> getMessages() {
        log.info("Fetching all users ");
        return messageRepo.findAll();
    }

    @Override
    public List<Sport> getSports() {
        log.info("Fetching all users ");
        return sportRepo.findAll();
    }

    @Override
    public List<Team> getTeams() {
        log.info("Fetching all users ");
        return teamRepo.findAll();
    }

    @Override
    public UUID deleteUserById(UUID id){
        userRepo.deleteById(id);
        log.info("Delete user with id:" + id);
        return id;
    }

    @Override
    public UUID deleteRoleById(UUID id){
        roleRepo.deleteById(id);
        log.info("Delete rele with id:" + id);
        return id;
    }

    @Override
    public UUID deleteMessageById(UUID id){
        messageRepo.deleteById(id);
        log.info("Delete message with id:" + id);
        return id;
    }

    @Override
    public UUID deleteTeamById(UUID id){
        teamRepo.deleteById(id);
        log.info("Delete user with id:" + id);
        return id;
    }

    @Override
    public UUID deleteEventById(UUID id){
        eventRepo.deleteById(id);
        log.info("Delete user with id:" + id);
        return id;
    }

    @Override
    public UUID deleteSportById(UUID id){
        sportRepo.deleteById(id);
        log.info("Delete user with id:" + id);
        return id;
    }

    @Override
    public void deleteEventLegs(UUID id){
         eventRepo.deleteEventLegs(id);
    }

    @Override
    public void deleteEventUsersLegs(UUID id){
        eventRepo.deleteEventUsersLegs(id);
    }

    @Override
    public void deleteEventInterestsLegs(UUID id){
        eventRepo.deleteEventInterestsLegs(id);
    }

    @Override
    public void deleteEventOrganiserLegs(UUID id){
        eventRepo.deleteEventOrganiserLegs(id);
    }

    @Override
    public void deleteSportLegs(UUID id){
         sportRepo.deleteSportLegs(id);
    }

    @Override
    public void deleteUserEventsLegs(UUID id){
        userRepo.deleteUserEventsLegs(id);
    }

    @Override
    public void deleteUserInterestsLegs(UUID id){
        userRepo.deleteUserInterestsLegs(id);
    }

    @Override
    public void deleteUserOrganiserLegs(UUID id){
        userRepo.deleteUserOrganiserLegs(id);
    }

    @Override
    public void deleteUserCaptainLegs(UUID id){
        userRepo.deleteUserCaptainLegs(id);
    }

    @Override
    public void deleteUserTeammatesLegs(UUID id){
        userRepo.deleteUserTeammatesLegs(id);
    }

    @Override
    public void deleteTeamCaptainLegs(UUID id){
        teamRepo.deleteTeamCaptainLegs(id);
    }

    @Override
    public void deleteTeamTeammatesLegs(UUID id){
        teamRepo.deleteTeamTeammatesLegs(id);
    }

}
