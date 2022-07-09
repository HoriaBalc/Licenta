package io.getarrays.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.FetchType.*;
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Date date;
    private String gender;
    private String verificationCode;
    private boolean enabled;
    public String urlImage;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = EAGER)
    private Collection<SportEvent> interests = new ArrayList<>();

    @OneToMany(fetch = EAGER)
    @JsonIgnore
    private Collection<SportEvent> events = new ArrayList<>();

    @OneToMany(fetch = EAGER)
    @JsonIgnore
    private Collection<Team> captainTeams = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Collection<Team> getCaptainTeams() {
        return captainTeams;
    }

    public void setCaptainTeams(Collection<Team> captainTeams) {
        this.captainTeams = captainTeams;
    }

    public Collection<SportEvent> getInterests() {
        return interests;
    }

    public void setInterests(Collection<SportEvent> interests) {
        this.interests = interests;
    }

    public Collection<SportEvent> getEvents() {
        return events;
    }

    public void setEvents(Collection<SportEvent> events) {
        this.events = events;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
