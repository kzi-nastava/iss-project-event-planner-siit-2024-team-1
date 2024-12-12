package com.example.eventplanner.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOverviewDTO {
    int id;
    String email;
    String firstName;
    String lastName;
    String profilePicture;
}
