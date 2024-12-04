package com.example.eventplanner.dto.user.update;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateSpRequestDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private AddressDTO address;
    private String password;
    private String photo;
    private Role role;

    private String description;
    private List<String> photos;
}
