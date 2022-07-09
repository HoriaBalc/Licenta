package io.getarrays.userservice.domain;

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
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @OneToMany( fetch = EAGER)
    private Collection<SportEvent> sportEvents = new ArrayList<>();

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

    public Collection<SportEvent> getSportEvents() {
        return sportEvents;
    }

    public void setSportEvents(Collection<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }
}
