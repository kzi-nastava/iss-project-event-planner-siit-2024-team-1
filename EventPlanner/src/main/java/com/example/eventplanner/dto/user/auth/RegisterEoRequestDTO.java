package com.example.eventplanner.dto.user.auth;

import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterEoRequestDTO {
    private int id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;
    private String password;
    private String photo;
    private Role role;
}
