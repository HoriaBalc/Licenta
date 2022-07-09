package io.getarrays.userservice.domain;

import io.getarrays.userservice.repo.EventRepo;
import io.getarrays.userservice.repo.SportRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String code;
    private int participants;
    @ManyToMany(fetch = EAGER)
    private Collection<AppUser> teammates = new ArrayList<>();

    @ManyToOne(fetch = EAGER)
    private SportEvent sportEvent;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<AppUser> getTeammates() {
        return teammates;
    }

    public void setTeammates(Collection<AppUser> teammates) {
        this.teammates = teammates;
    }

    public SportEvent getSportEvent() {
        return sportEvent;
    }

    public void setSportEvent(SportEvent sportEvent) {
        this.sportEvent = sportEvent;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}
