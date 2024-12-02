package com.example.eventplanner.dto.user.auth;

import com.example.eventplanner.model.common.Address;

import java.util.List;

public class RegisterEoRequestResponseDTO {
    private int id;
    private String message;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;
    private String photo;
    private String accessToken;
    private String refreshToken;

    public RegisterEoRequestResponseDTO(int id, String message, String name, String surname, String phoneNumber, Address address, String email, String photo, String accessToken, String refreshToken) {
        this.id = id;
        this.message = message;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.photo = photo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
