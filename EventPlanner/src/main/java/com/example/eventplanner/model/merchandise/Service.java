package com.example.eventplanner.model.merchandise;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@DiscriminatorValue("Service")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service extends Merchandise {
    @OneToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "timeslot_id"))
    List<Timeslot> timeslots;
}
