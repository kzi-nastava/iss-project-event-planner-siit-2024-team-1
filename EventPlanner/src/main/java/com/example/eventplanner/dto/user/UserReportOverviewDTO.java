package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.UserReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportOverviewDTO {
    private int id;
    private int reportedUserId;
    private int reporterId;
    private String reporterEmail;
    private String reportedUserName;
    private String reportedUserSurname;
    private String reportedUserEmail;


    private String reason;
    private UserReportStatus status;
}
