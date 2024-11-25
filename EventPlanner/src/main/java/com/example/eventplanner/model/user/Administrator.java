package com.example.eventplanner.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@DiscriminatorValue("Administrator")
@Data
public class Administrator extends User {
    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    List<UserReport> reports;
}
