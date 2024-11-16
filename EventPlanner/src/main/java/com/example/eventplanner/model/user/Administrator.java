package com.example.eventplanner.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends User {
    // Additional properties specific to Administrator

    // Getters and Setters
}
