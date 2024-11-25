package com.example.eventplanner.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class AllUserReportsDTO {
    private List<UserReportDTO> reports;
}
