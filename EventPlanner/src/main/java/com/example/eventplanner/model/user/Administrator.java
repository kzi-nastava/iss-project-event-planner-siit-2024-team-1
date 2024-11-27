package com.example.eventplanner.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("Administrator")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends User {
    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    List<UserReport> reports;
}
