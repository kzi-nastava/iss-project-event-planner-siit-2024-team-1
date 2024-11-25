package com.example.eventplanner.dto.user;

import lombok.Data;

import java.util.Date;
@Data
public class UserReportResponseDTO {
    private int id;
    private Date approvalDate;
    private boolean status;

}
