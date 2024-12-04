package com.example.eventplanner.dto.user.update;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.auth.Role;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateSpResponseDTO {
    private int id;
    private String name;
    private String message;
    private String surname;
    private String phoneNumber;
    private AddressDTO address;
    private String photo;
    private Role role;

    private String description;
    private List<String> photos;

    public UpdateSpResponseDTO(int id, String message, String name, String surname, String phoneNumber, AddressDTO address,  String photo,  String description, List<String> photos) {
        this.id = id;
        this.message = message;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.photo = photo;
        this.description = description;
        this.photos = photos;
    }
}
