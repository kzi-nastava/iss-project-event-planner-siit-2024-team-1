package com.example.eventplanner.dto.user.auth;

import com.example.eventplanner.model.common.Address;

import java.util.List;

public class RegisterSpRequestResponseDTO {
    private int id;
    private String message;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;
    private String photo;

    private String company;
    private String description;
    private List<String> photos;

    private String accessToken;
    private String refreshToken;

    public RegisterSpRequestResponseDTO(int id, String message, String name, String surname, String phoneNumber, Address address, String email, String photo, String company, String description, List<String> photos, String accessToken, String refreshToken) {
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
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
