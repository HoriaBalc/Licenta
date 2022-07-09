package io.getarrays.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private Date startDate;
    private Integer hour;
    private Integer minutes;
    private Date endEnrollDate;
    private String address;
    private Integer price;
    private boolean hide;
    private Integer participants;
    private Integer teams;
    private String latitude;
    private String longitude;
    private Integer maxParticipants;
    private Integer maxTeams;
    private Integer persPerTeam;
    private String urlImage;
    private String gen;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private Collection<AppUser> users = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getMaxTeams() {
        return maxTeams;
    }

    public void setMaxTeams(Integer maxTeams) {
        this.maxTeams = maxTeams;
    }

    public Integer getTeams() {
        return teams;
    }

    public void setTeams(Integer teams) {
        this.teams = teams;
    }

    public Integer getPersPerTeam() {
        return persPerTeam;
    }

    public void setPersPerTeam(Integer persPerTeam) {
        this.persPerTeam = persPerTeam;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndEnrollDate() {
        return endEnrollDate;
    }

    public void setEndEnrollDate(Date endEnrollDate) {
        this.endEnrollDate = endEnrollDate;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Collection<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<AppUser> users) {
        this.users = users;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
