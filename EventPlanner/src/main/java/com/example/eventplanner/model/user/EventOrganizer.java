package com.example.eventplanner.model.user;

import com.example.eventplanner.model.event.Event;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("EventOrganizer")
public class EventOrganizer extends User {
    @OneToMany
    private List<Event> organizingEvents;

}