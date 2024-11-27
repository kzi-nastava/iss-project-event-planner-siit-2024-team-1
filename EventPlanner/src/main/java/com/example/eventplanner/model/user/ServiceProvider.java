package com.example.eventplanner.model.user;

import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("ServiceProvider")
public class ServiceProvider extends User {
    private String company;
    private String description;

    @OneToMany(mappedBy = "serviceProvider")
    private List<BusinessPhoto> photos;

    @OneToMany
    private List<Merchandise> merchandise;
}