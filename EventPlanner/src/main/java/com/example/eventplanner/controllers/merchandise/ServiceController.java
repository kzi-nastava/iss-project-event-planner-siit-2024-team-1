package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.filter.EventFiltersDTO;
import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.filter.ServiceFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetAllServicesResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.FilterServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.create.CreateServiceResponseDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceRequestDTO;
import com.example.eventplanner.dto.merchandise.service.update.UpdateServiceResponseDTO;
import com.example.eventplanner.services.merchandise.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;


    @PostMapping("/{serviceId}/reserve")
    public ResponseEntity<ReservationResponseDTO> reserveService(
            @PathVariable int serviceId,
            @Valid @RequestBody ReservationRequestDTO request) {
        ReservationResponseDTO response = serviceService.reserveService(serviceId, request);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewMerchandiseResponseDTO> addReview(@RequestBody ReviewMerchandiseRequestDTO request, @PathVariable int id) {
        return ResponseEntity.ok(new ReviewMerchandiseResponseDTO());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> filterServices(
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer durationMin,
            @RequestParam(required = false) Integer durationMax,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        ServiceFiltersDTO productFiltersDTO=new ServiceFiltersDTO(priceMin,priceMax,category,durationMin,durationMax,city);

        return ResponseEntity.ok(serviceService.search(productFiltersDTO,search,pageable));
    }

}
