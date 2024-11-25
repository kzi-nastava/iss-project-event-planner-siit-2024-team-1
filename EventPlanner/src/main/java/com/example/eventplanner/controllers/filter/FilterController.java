package com.example.eventplanner.controllers.filter;

import com.example.eventplanner.dto.filter.FilterRequestDTO;
import com.example.eventplanner.dto.filter.FilterResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/filter")
public class FilterController {
    @PostMapping
    public ResponseEntity<FilterResponseDTO> applyFilters(@RequestBody FilterRequestDTO request) {
        return ResponseEntity.ok(new FilterResponseDTO());
    }
}
