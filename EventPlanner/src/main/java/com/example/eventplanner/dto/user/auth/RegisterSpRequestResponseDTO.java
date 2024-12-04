package com.example.eventplanner.dto.user.auth;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RegisterSpRequestResponseDTO {
    private int id;
    private String message;
    private String name;
    private String surname;
    private String phoneNumber;
    private AddressDTO address;
    private String email;
    private String photo;

    private String company;
    private String description;
    private List<String> photos;

    private String accessToken;
    private String refreshToken;

    public RegisterSpRequestResponseDTO(int id, String message, String name, String surname, String phoneNumber, AddressDTO address, String email, String photo, String company, String description, List<String> photos, String accessToken, String refreshToken) {
        this.id = id;
        this.message = message;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.photo = photo;
        this.company = company;
        this.description = description;
        this.photos = photos;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
