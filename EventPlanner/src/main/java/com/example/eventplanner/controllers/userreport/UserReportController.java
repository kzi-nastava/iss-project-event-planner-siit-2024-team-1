package com.example.eventplanner.controllers.userreport;

import com.example.eventplanner.dto.user.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user-reports")
public class UserReportController {
    @PostMapping
    public ResponseEntity<UserReportResponseDTO> reportUser(
            @RequestBody UserReportDTO reportRequest
    ) {

        return ResponseEntity.ok(new UserReportResponseDTO());
    }

    @GetMapping
    public ResponseEntity<AllUserReportsDTO> getAllReports(){
        return ResponseEntity.ok(new AllUserReportsDTO());
    }

    @PostMapping("/{reportId}/suspend")
    public ResponseEntity<UserSuspensionDTO> suspendUser(@PathVariable Long reportId) {
        return ResponseEntity.ok(new UserSuspensionDTO());
    }

    @GetMapping("/users/{userId}/suspension")
    public ResponseEntity<SuspensionStatusDTO> checkSuspensionStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(new SuspensionStatusDTO());
    }

}
