package com.example.eventplanner.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("AuthenticatedUser")
public class AuthenticatedUser extends User {
    // Additional properties specific to AuthenticatedUser

    // Getters and Setters
}