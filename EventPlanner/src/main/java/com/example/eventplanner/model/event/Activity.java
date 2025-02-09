package com.example.eventplanner.model.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.common.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private Address address;

}
