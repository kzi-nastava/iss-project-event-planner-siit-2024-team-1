package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetAllServicesResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.FilterServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceResponseDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @GetMapping()
    public GetAllServicesResponseDTO GetAll() {
        return new GetAllServicesResponseDTO();
    }

    @GetMapping("sp/{id}")
    public GetAllServicesResponseDTO GetAllById(@PathVariable( value = "id") int id) {
        return new GetAllServicesResponseDTO();
    }

    @GetMapping("/{id}")
    public GetServiceByIdResponseDTO GetById(@PathVariable( value = "id") int id) {
        return new GetServiceByIdResponseDTO();
    }

    @GetMapping()
    public GetAllServicesResponseDTO GetFiltered(@RequestBody FilterServiceRequestDTO filterRequest) {
        return new GetAllServicesResponseDTO();
    }

    @GetMapping("/{title}")
    public GetServiceByIdResponseDTO GetByTitle(@PathVariable( value = "title") String title) {
        return new GetServiceByIdResponseDTO();
    }

    @PostMapping()
    public CreateServiceResponseDTO Create(@RequestBody CreateServiceRequestDTO request) {
        return new CreateServiceResponseDTO();
    }

    @PutMapping()
    public UpdateServiceResponseDTO Update(@RequestBody UpdateServiceRequestDTO request) {
        return new UpdateServiceResponseDTO();
    }

    @DeleteMapping("/{id}")
    public String Delete(@PathVariable int id) {
        return "success";
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewMerchandiseResponseDTO> addReview(@RequestBody ReviewMerchandiseRequestDTO request, @PathVariable int id) {
        return ResponseEntity.ok(new ReviewMerchandiseResponseDTO());
    }
}
