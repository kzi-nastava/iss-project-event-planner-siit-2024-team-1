package com.example.eventplanner.dto.user.auth;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RegisterSpRequestDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private AddressDTO address;
    private String email;
    private String password;
    private String photo;
    private Role role;

    private String company;
    private String description;
    private List<Integer> photos;
}
