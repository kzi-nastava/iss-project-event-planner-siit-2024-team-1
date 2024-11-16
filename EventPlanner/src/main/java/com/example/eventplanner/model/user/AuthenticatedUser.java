package com.example.eventplanner.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AuthenticatedUser")
public class AuthenticatedUser extends User {
    // Additional properties specific to AuthenticatedUser

    // Getters and Setters
}