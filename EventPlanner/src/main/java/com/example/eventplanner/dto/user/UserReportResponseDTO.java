package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.UserReportStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReportResponseDTO {
    private int id;
    private int reportedUserId;
    private int reporterId;

    private String reason;
    private UserReportStatus status;

}
