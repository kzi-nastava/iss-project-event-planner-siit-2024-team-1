package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {
    @PostMapping("/{serviceId}/reserve")
    public ResponseEntity<ReservationResponseDTO> reserveService(
            @PathVariable Long serviceId,
            @Valid @RequestBody ReservationRequestDTO request) {
        return ResponseEntity.ok(new ReservationResponseDTO());
    }

}
