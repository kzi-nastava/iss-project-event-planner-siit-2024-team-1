package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.User;
import lombok.Data;


import java.util.Date;
@Data
public class UserReportDTO {
    private int reportedUserId;
    private int reporterId;

    private String reason;
}
