package com.example.eventplanner.model.user;

import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ServiceProvider")
public class ServiceProvider extends User {
    private String company;
    private String description;

    @OneToMany(mappedBy = "serviceProvider")
    private List<BusinessPhoto> photos;

    @OneToMany
    private List<Merchandise> merchandise;

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<BusinessPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BusinessPhoto> photos) {
        this.photos = photos;
    }

    public List<Merchandise> getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(List<Merchandise> merchandise) {
        this.merchandise = merchandise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}