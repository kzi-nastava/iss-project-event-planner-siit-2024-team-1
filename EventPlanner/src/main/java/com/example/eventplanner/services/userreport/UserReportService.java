package com.example.eventplanner.services.userreport;

import com.example.eventplanner.dto.user.UserReportDTO;
import com.example.eventplanner.dto.user.UserReportOverviewDTO;
import com.example.eventplanner.dto.user.UserReportResponseDTO;
import com.example.eventplanner.dto.user.UserSuspensionDTO;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.model.user.UserReport;
import com.example.eventplanner.model.user.UserReportStatus;
import com.example.eventplanner.model.user.UserSuspension;
import com.example.eventplanner.repositories.userreport.UserReportRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.repositories.userreport.UserSuspensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportService {

    private final UserReportRepository userReportRepository;
    private final UserRepository userRepository;
    private final UserSuspensionRepository userSuspensionRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public UserReportResponseDTO createReport(UserReportDTO userReportDTO) {
        // Fetch the reporter and reported user from UserRepository
        User reporter = userRepository.findById(userReportDTO.getReporterId())
                .orElseThrow(() -> new RuntimeException("Reporter not found with ID: " + userReportDTO.getReporterId()));

        User reportedUser = userRepository.findById(userReportDTO.getReportedUserId())
                .orElseThrow(() -> new RuntimeException("Reported user not found with ID: " + userReportDTO.getReportedUserId()));

        // Create a new UserReport object
        UserReport userReport = new UserReport();
        userReport.setReporter(reporter);
        userReport.setReportedUser(reportedUser);
        userReport.setReason(userReportDTO.getReason());
        userReport.setStatus(UserReportStatus.PENDING); // Default approval status
        userReport.setApprovalDate(null); // Approval date not set

        // Save the report to the database
        UserReport savedReport = userReportRepository.save(userReport);

        // Map the saved UserReport entity to UserReportResponseDTO
        UserReportResponseDTO responseDTO = new UserReportResponseDTO();
        responseDTO.setId(savedReport.getId());
        responseDTO.setReporterId(savedReport.getReporter().getId());
        responseDTO.setReportedUserId(savedReport.getReportedUser().getId());
        responseDTO.setReason(savedReport.getReason());
        responseDTO.setStatus(savedReport.getStatus());


        // Return the response DTO
        return responseDTO;
    }

    public List<UserReportOverviewDTO> getAllPendingReports() {
        // Fetch all user reports with status "PENDING" from the database
        List<UserReport> pendingReports = userReportRepository.findAll()
                .stream()
                .filter(report -> report.getStatus() == UserReportStatus.PENDING)
                .toList();

        // Map each UserReport entity to UserReportOverviewDTO
        return pendingReports.stream()
                .map(report -> new UserReportOverviewDTO(
                        report.getId(),
                        report.getReportedUser().getId(),
                        report.getReporter().getId(),
                        report.getReporter().getUsername(),
                        report.getReportedUser().getName(),
                        report.getReportedUser().getSurname(),
                        report.getReportedUser().getUsername(),
                        report.getReason(),
                        report.getStatus()
                ))
                .collect(Collectors.toList());
    }


    public UserSuspensionDTO approveReportAndSuspendUser(int reportId) {
        // Fetch the report by ID
        UserReport userReport = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("User report not found with ID: " + reportId));

        // Approve the report and set the approval date
        userReport.setStatus(UserReportStatus.APPROVED);
        userReport.setApprovalDate(new Date()); // Use the current date
        userReportRepository.save(userReport); // Save the updated report

        // Fetch the reported user
        User reportedUser = userReport.getReportedUser();

        // Check if the user already has an active suspension
        Optional<UserSuspension> existingSuspension = userSuspensionRepository.findByUserAndStartTimeBeforeAndEndTimeAfter(
                reportedUser, LocalDateTime.now(), LocalDateTime.now()
        );

        UserSuspension suspension;
        if (existingSuspension.isPresent()) {
            // If an active suspension exists, renew it to last for another 3 days
            suspension = existingSuspension.get();
            suspension.setEndTime(LocalDateTime.now().plusDays(3)); // Extend suspension by 3 days
            userSuspensionRepository.save(suspension);
        } else {
            // Otherwise, create a new suspension for 3 days
            suspension = new UserSuspension();
            suspension.setUser(reportedUser);
            suspension.setStartTime(LocalDateTime.now());
            suspension.setEndTime(LocalDateTime.now().plusDays(3)); // Lasts for 3 days
            suspension.setReason(userReport.getReason()); // Reason from the report
            userSuspensionRepository.save(suspension);
        }

        messagingTemplate.convertAndSendToUser(
                String.valueOf(suspension.getUser().getId()),
                "/suspensions",
                new UserSuspensionDTO(
                        suspension.getId(),
                        suspension.getUser().getId(),
                        suspension.getStartTime(),
                        suspension.getEndTime(),
                        suspension.getReason()
                )
        );

        // Map the UserSuspension entity to UserSuspensionDTO
        return new UserSuspensionDTO(
                suspension.getId(),
                suspension.getUser().getId(),
                suspension.getStartTime(),
                suspension.getEndTime(),
                suspension.getReason()
        );
    }

    public UserReportResponseDTO denyReport(int reportId) {
        // Fetch the report by ID
        UserReport userReport = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("User report not found with ID: " + reportId));

        // Set the report status to debied
        userReport.setStatus(UserReportStatus.DENIED);
        userReport.setApprovalDate(new Date()); // Use the current date for the rejection date
        userReportRepository.save(userReport); // Save the updated report

        // Map the denied report to a response DTO
        return new UserReportResponseDTO(
                userReport.getId(),
                userReport.getReporter().getId(),
                userReport.getReportedUser().getId(),
                userReport.getReason(),
                userReport.getStatus()
        );
    }

    public UserSuspensionDTO getSuspension(User user) {
        Optional<UserSuspension> existingSuspension = userSuspensionRepository.findByUserAndStartTimeBeforeAndEndTimeAfter(
                user, LocalDateTime.now(), LocalDateTime.now()
        );
        if (existingSuspension.isEmpty()) {
            return null;
        }

        UserSuspension suspension = existingSuspension.get();

        return new UserSuspensionDTO(
                suspension.getId(),
                suspension.getUser().getId(),
                suspension.getStartTime(),
                suspension.getEndTime(),
                suspension.getReason()
        );
    }

}