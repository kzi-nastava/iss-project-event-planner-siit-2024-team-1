package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.User;
import lombok.Data;


import java.util.Date;
@Data
public class UserReportDTO {
    private int id;

    private User reportedUser;
    private User reporter;

    private String reason;
}
