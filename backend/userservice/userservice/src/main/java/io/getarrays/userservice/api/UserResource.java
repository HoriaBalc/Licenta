package io.getarrays.userservice.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import io.getarrays.userservice.domain.*;
import io.getarrays.userservice.filter.CustomAuthenticationFilter;
import io.getarrays.userservice.repo.EventRepo;
import io.getarrays.userservice.repo.UserRepo;
import io.getarrays.userservice.security.SecurityConfig;
import io.getarrays.userservice.service.EmailServiceImpl;
import io.getarrays.userservice.service.UserService;
import io.getarrays.userservice.service.UserServiceImpl;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;

    private final AuthenticationManager authenticationManager;

    @SendTo("/topic/message")
    public Message broadcastMessage(@Payload Message textMessageDTO) {
        return textMessageDTO;
    }

    @GetMapping("/users/page")
    public ResponseEntity<List<AppUser>>getUsersByPage(@RequestBody int page){
        return ResponseEntity.ok().body(userService.getUsersByPage(page));
    }

    @GetMapping("/sports/sortname")
    public ResponseEntity<List<Sport>>getSportsSortName(){
        return ResponseEntity.ok().body(userService.getSportsSortName());
    }

    @GetMapping("/sports/sortname/desc")
    public ResponseEntity<List<Sport>>getSportsSortNameDesc(){
        return ResponseEntity.ok().body(userService.getSportsSortNameDesc());
    }

    @GetMapping("/events/sortname/desc")
    public ResponseEntity<List<SportEvent>>getEventsSortNameDesc(){
        return ResponseEntity.ok().body(userService.getEventsSortNameDesc());
    }

    @GetMapping("/events/sortname")
    public ResponseEntity<List<SportEvent>>getEventsSortName(){
        return ResponseEntity.ok().body(userService.getEventsSortName());
    }

    @GetMapping("/events/sortprice")
    public ResponseEntity<List<SportEvent>>getEventsSortPrice(){
        return ResponseEntity.ok().body(userService.getEventsSortPrice());
    }

    @GetMapping("/events/sortprice/desc")
    public ResponseEntity<List<SportEvent>>getEventsSortPriceDesc(){
        return ResponseEntity.ok().body(userService.getEventsSortPriceDesc());
    }

    @GetMapping("/users/sortname")
    public ResponseEntity<List<AppUser>>getUsersSortName(){
        return ResponseEntity.ok().body(userService.getUsersSortName());
    }

    @GetMapping("/users/sortname/desc")
    public ResponseEntity<List<AppUser>>getUsersSortNameDesc(){
        return ResponseEntity.ok().body(userService.getUsersSortNameDesc());
    }

    @GetMapping("/users/sortusername")
    public ResponseEntity<List<AppUser>>getUsersSortUsername(){
        return ResponseEntity.ok().body(userService.getUsersSortUsername());
    }

    @GetMapping("/users/sortusername/desc")
    public ResponseEntity<List<AppUser>>getUsersSortUsernameDesc(){
        return ResponseEntity.ok().body(userService.getUsersSortUsernameDesc());
    }

    @GetMapping("/users/sortemail")
    public ResponseEntity<List<AppUser>>getUsersSortEmail(){
        return ResponseEntity.ok().body(userService.getUsersSortEmail());
    }

    @GetMapping("/users/sortemail/desc")
    public ResponseEntity<List<AppUser>>getUsersSortEmailDesc(){
        return ResponseEntity.ok().body(userService.getUsersSortEmailDesc());
    }

    @GetMapping("/users/eventCreator")
    public ResponseEntity<AppUser>getUserByEvent(@RequestParam String name){
        List<AppUser> users = userService.getUsers();
        SportEvent event = userService.getEvent(name);
        for(AppUser user: users){
            if(user.getEvents().size()>0){
                System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                Collection<SportEvent> events=user.getEvents();
                if(events.contains(event)){
                    System.out.println("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    return ResponseEntity.ok().body(user);
                };
            }
        }
        System.out.println("NUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUA");
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users/organisator")
    public ResponseEntity<Collection<SportEvent>>getUserOrganisators(@RequestParam String name){
        AppUser user = userService.getUser(name);

        return ResponseEntity.ok().body(user.getEvents());

        //return ResponseEntity.ok().body(null);
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>>getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>>getRoles(){
        return ResponseEntity.ok().body(userService.getRoles());
    }


    @GetMapping("/sports")
    public ResponseEntity<List<Sport>>getSports(){
        return ResponseEntity.ok().body(userService.getSports());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>>getMessages(){
        return ResponseEntity.ok().body(userService.getMessages());
    }

    @GetMapping("/events")
    public ResponseEntity<List<SportEvent>>getEvents(){
        return ResponseEntity.ok().body(userService.getEvents());
    }

    @GetMapping("/teams")
    public ResponseEntity<List<Team>>getTeams(){
        return ResponseEntity.ok().body(userService.getTeams());
    }

    @PostMapping("/user/save")
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser appUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        AppUser appUserCopy= userRepo.findByUsername(appUser.getUsername());
        AppUser appUserCopyEmail= userRepo.findByEmail(appUser.getEmail());
        if(appUserCopy==null && appUserCopyEmail==null){
            log.info("UserDetails asdasdsad ");
            String username = appUser.getUsername();
            String password = appUser.getPassword();
            if(!appUser.isEnabled())
                appUser.setEnabled(false);
            String randomCode = RandomString.make(10);
            appUser.setVerificationCode(randomCode);
            log.info("Username is: {}", username);
            log.info("Password is: {}", password);
            return ResponseEntity.created(uri).body(userService.saveUser(appUser));
        }
        else {if( appUser.getId()!=null && appUser.isEnabled()){
            return ResponseEntity.created(uri).body(userService.saveUser(appUser));
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }}
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/message/save")
    public ResponseEntity<Message>saveMessage(@RequestBody Message message){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/message/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveMessage(message));
    }

    @PostMapping("/sport/save")
    public ResponseEntity<Sport>saveSport(@RequestBody Sport sport){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/sport/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveSport(sport));
    }

    @PostMapping("/team/save")
    public ResponseEntity<Team>saveTeam(@RequestBody Team team){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/team/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveTeam(team));
    }

    @PostMapping("/event/save")
    public ResponseEntity<SportEvent>saveEvent(@RequestBody SportEvent event){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/event/save").toUriString());
        SportEvent sportEvent= eventRepo.findByName(event.getName());
        if(sportEvent==null || event.getId()!=null)
        return ResponseEntity.created(uri).body(userService.saveEvent(event));
        return ResponseEntity.badRequest().body(null);
    }


    @GetMapping(value = "/role")
    public ResponseEntity<Role>getRole(@RequestParam String name){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        Role role=userService.getRole(name);
        return ResponseEntity.ok(role);
    }

    @GetMapping(value = "/event")
    public ResponseEntity<SportEvent>getEvent(@RequestParam String name){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        SportEvent event=userService.getEvent(name);
        return ResponseEntity.ok(event);
    }

    @GetMapping(value = "/sport")
    public ResponseEntity<Sport>getSport(@RequestParam String name){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        Sport sport=userService.getSport(name);
        System.out.println(sport.getName() +"   sadsadasdasdasdasdasdasd");
        return ResponseEntity.ok(sport);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<AppUser>getUser(@RequestParam String username){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        AppUser user=userService.getUser(username);
        System.out.println(user.getUsername() +"   sadsadasdasdasdasdasdasd");
        return ResponseEntity.ok(user);
    }
    @GetMapping(value = "/user/email")
    public ResponseEntity<AppUser>getUserByEmail(@RequestParam String email){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        AppUser user=userService.getUserByEmail(email);
        System.out.println(user.getUsername() +"   sadsadasdasdasdasdasdasd");
        if(user!=null){
            System.out.println("pulanarecarte");
            EmailServiceImpl.sendPasswordResetEmail(user);
        }
        return ResponseEntity.ok(user);
    }
    @GetMapping(value = "/team")
    public ResponseEntity<Team>getTeam(@RequestParam String name){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/name").toUriString());
        Team team=userService.getTeam(name);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<UUID> deleteUserById(@RequestParam String username) {
        AppUser user = userService.getUser(username);
        userService.deleteUserCaptainLegs(user.getId());
        userService.deleteUserOrganiserLegs(user.getId());
        userService.deleteUserEventsLegs(user.getId());
        userService.deleteUserInterestsLegs(user.getId());
        userService.deleteUserTeammatesLegs(user.getId());
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/role")
    public ResponseEntity<UUID> deleteRoleById(@RequestParam String name) {
        Role role = userService.getRole(name);
        userService.deleteRoleById(role.getId());
        return new ResponseEntity<>(role.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/message")
        public ResponseEntity<UUID> deleteMessageById(@RequestParam UUID id) {
        userService.deleteMessageById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping(value = "/team")
    public ResponseEntity<UUID> deleteTeamById(@RequestParam String name) {
        Team team = userService.getTeam(name);
        userService.deleteTeamById(team.getId());
        return new ResponseEntity<>(team.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/event")
    public ResponseEntity<UUID> deleteEventById(@RequestParam String name) {
        SportEvent event = userService.getEvent(name);
        List<Team> teams= userService.getTeams();
        teams.forEach(team -> { if(team.getSportEvent().equals(event)) team.setSportEvent(null); });
        userService.deleteEventLegs(event.getId());
        userService.deleteEventUsersLegs(event.getId());
        userService.deleteEventInterestsLegs(event.getId());
        userService.deleteEventOrganiserLegs(event.getId());
        userService.deleteEventById(event.getId());
        return new ResponseEntity<>(event.getId(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/sport")
    public ResponseEntity<UUID> deleteSportById(@RequestParam String name) {
        Sport sport = userService.getSport(name);
        System.out.println("asddghkl " + sport.getId());
        userService.deleteSportLegs(sport.getId());
        userService.deleteSportById(sport.getId());
        return new ResponseEntity<>(sport.getId(), HttpStatus.OK);
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<Role>addRoleToUser(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role/changeToUser")
    public ResponseEntity<Role>changeRoleToUser(@RequestBody RoleToUserForm form){
        userService.changeRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role/changeRole")
    public ResponseEntity<Role>changeRole(@RequestBody RoleToUserForm form){
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/addToUser")
    public ResponseEntity<Team>addCaptainTeamToUser(@RequestBody TeamToUserForm form){
        userService.addCaptainTeamToUser(form.getUsername(), form.getTeamName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/addToTeam")
    public ResponseEntity<AppUser>addUserToTeam(@RequestBody TeamToUserForm form){
        userService.addUserToTeam(form.getUsername(), form.getTeamName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/event/addToTeam")
    public ResponseEntity<SportEvent>addEventToTeam(@RequestBody EventToTeamForm form){
        userService.addEventToTeam(form.getTeamName(),form.getEventName());
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/team/addToEvent")
//    public ResponseEntity<SportEvent>addTeamToEvent(@RequestBody EventToTeamForm form){
//        userService.addTeamToEvent(form.getTeamName(),form.getEventName());
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/user/addToEvent")
    public ResponseEntity<AppUser>addUserToEvent(@RequestBody UserToEventForm form){
        userService.addUserToEvent(form.getUsername(),form.getEventName() );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/event/addToSport")
    public ResponseEntity<SportEvent>addEventToSport(@RequestBody EventToSportForm form){
        userService.addEventToSport(form.getEventName(),form.getSportName() );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/event/addToUser")
    public ResponseEntity<SportEvent>addEventToUser(@RequestBody UserToEventForm form){
        userService.addEventToUser(form.getUsername(),form.getEventName() );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/event/addInterestToUser")
    public ResponseEntity<SportEvent>addEventInterestToUser(@RequestBody UserToEventForm form){
        userService.addEventInterestToUser(form.getUsername(),form.getEventName() );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/event/deleteInterestToUser")
    public ResponseEntity<SportEvent>deleteEventInterestToUser(@RequestBody UserToEventForm form){
        userService.deleteEventInterestToUser(form.getUsername(),form.getEventName() );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader =  request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user =userService.getUser(username);
                String access_token= JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String > tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String > error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
             throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}

@Data
class TeamToUserForm {
    private String username;
    private String teamName;
}

@Data
class EventToTeamForm {
    private String eventName;
    private String teamName;
}

@Data
class UserToEventForm {
    private String username;
    private String eventName;
}

@Data
class StringForm {
    private String direction;
}

@Data
class EventToSportForm {
    private String eventName;
    private String sportName;
}

