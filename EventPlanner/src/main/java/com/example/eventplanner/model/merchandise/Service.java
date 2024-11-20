package com.example.eventplanner.model.merchandise;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Service")
public class Service extends Merchandise {
}
