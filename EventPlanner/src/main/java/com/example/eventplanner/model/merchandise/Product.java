package com.example.eventplanner.model.merchandise;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Product")
public class Product extends Merchandise {
}
