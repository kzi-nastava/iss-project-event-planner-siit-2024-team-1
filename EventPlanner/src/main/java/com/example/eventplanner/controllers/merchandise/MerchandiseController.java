package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.services.merchandise.MerchandiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/merchandise")
@RequiredArgsConstructor
public class MerchandiseController {
    private final MerchandiseService merchandiseService;

    @GetMapping("/top")
    public ResponseEntity<List<MerchandiseOverviewDTO>> getTopMerchandise() {
        return ResponseEntity.ok(merchandiseService.getTop());
    }
    @GetMapping("/all")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> getAllMerchandise(
            @PageableDefault(size = 10, sort = "price", direction = Sort.Direction.DESC) Pageable merchandisePage
    ) {
        return ResponseEntity.ok(merchandiseService.getAll(merchandisePage));
    }
}
