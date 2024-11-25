package com.example.eventplanner.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "reported_user_id", referencedColumnName = "id")
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "reporter_user_id", referencedColumnName = "id")
    private User reporter;

    private String reason;
    private Date approvalDate;
    private boolean status;
}

