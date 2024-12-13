package com.example.eventplanner.controllers.userreport;

import com.example.eventplanner.dto.user.*;

import com.example.eventplanner.services.userreport.UserReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user-reports")
@RequiredArgsConstructor
public class UserReportController {
    private final UserReportService userReportService;
    @PostMapping
    public ResponseEntity<UserReportResponseDTO> reportUser(
            @RequestBody UserReportDTO reportRequest
    ) {

        return ResponseEntity.ok(userReportService.createReport(reportRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserReportOverviewDTO>> getPendingReports(){
        return ResponseEntity.ok(userReportService.getAllPendingReports());
    }

    /**
     * Endpoint to approve a user report and suspend the reported user.
     *
     * @param reportId ID of the report to approve
     * @return A UserSuspensionDTO containing the suspension details
     */
    @PostMapping("/{reportId}/approve")
    public ResponseEntity<UserSuspensionDTO> approveReportAndSuspendUser(@PathVariable int reportId) {
        UserSuspensionDTO suspensionDTO = userReportService.approveReportAndSuspendUser(reportId);
        return ResponseEntity.ok(suspensionDTO); // Return 200 OK with suspension details
    }

    /**
     * Endpoint to deny a user report.
     *
     * @param reportId ID of the report to deny
     * @return A UserReportResponseDTO containing the report's updated information
     */
    @PostMapping("/{reportId}/deny")
    public ResponseEntity<UserReportResponseDTO> denyReport(@PathVariable int reportId) {
        UserReportResponseDTO responseDTO = userReportService.denyReport(reportId);
        return ResponseEntity.ok(responseDTO); // Return 200 OK with denied report details
    }

    @GetMapping("/users/{userId}/suspension")
    public ResponseEntity<SuspensionStatusDTO> checkSuspensionStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(new SuspensionStatusDTO());
    }

}
