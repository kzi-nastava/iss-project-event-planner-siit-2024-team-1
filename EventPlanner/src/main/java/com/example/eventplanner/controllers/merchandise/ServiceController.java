package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetAllServicesResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.FilterServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceResponseDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceResponseDTO;
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

    @GetMapping()
    public ResponseEntity<GetAllServicesResponseDTO> GetAll() {
        return ResponseEntity.ok( new GetAllServicesResponseDTO());
    }

    @GetMapping("sp/{id}")
    public ResponseEntity<GetAllServicesResponseDTO> GetAllById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(new GetAllServicesResponseDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetServiceByIdResponseDTO> GetById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(new GetServiceByIdResponseDTO());
    }

    @GetMapping("/filter")
    public ResponseEntity<GetAllServicesResponseDTO> GetFiltered(@RequestBody FilterServiceRequestDTO filterRequest) {
        return ResponseEntity.ok(new GetAllServicesResponseDTO());
    }

    @GetMapping("/{title}")
    public ResponseEntity<GetServiceByIdResponseDTO> GetByTitle(@PathVariable(value = "title") String title) {
        return ResponseEntity.ok(new GetServiceByIdResponseDTO());
    }

    @PostMapping()
    public ResponseEntity<CreateServiceResponseDTO> Create(@RequestBody CreateServiceRequestDTO request) {
        return ResponseEntity.ok(new CreateServiceResponseDTO());
    }

    @PutMapping()
    public ResponseEntity<UpdateServiceResponseDTO> Update(@RequestBody UpdateServiceRequestDTO request) {
        return ResponseEntity.ok(new UpdateServiceResponseDTO());
    }

    @DeleteMapping("/{id}")
    public String Delete(@PathVariable int id) {
        return "success";
    }

}
