package com.example.eventplanner.model.common;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String street;
    private String city;
    private int number;
    private double longitude;
    private double latitude;



}
