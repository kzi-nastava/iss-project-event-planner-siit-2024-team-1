package com.example.eventplanner.dto.event;

import com.example.eventplanner.dto.review.ReviewDTO;
import com.example.eventplanner.dto.user.UserOverviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventReportDTO {
    List<UserOverviewDTO> participants;
    List<ReviewDTO> reviews;
}
