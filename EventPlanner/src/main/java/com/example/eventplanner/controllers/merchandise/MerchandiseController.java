package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
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
public class MerchandiseController {
    @GetMapping("/top")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> getTopMerchandise(
            @PageableDefault(size = 10, sort = "popularity", direction = Sort.Direction.DESC) Pageable merchandisePage
    ) {
        // Get paginated merchandise from repository
        List<MerchandiseOverviewDTO> emptyDTOs = Collections.nCopies(merchandisePage.getPageSize(), new MerchandiseOverviewDTO());
        Page<MerchandiseOverviewDTO> merchandiseDTOs = new PageImpl<>(emptyDTOs, merchandisePage, 0);

        return ResponseEntity.ok(merchandiseDTOs);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> getAllMerchandise(
            @PageableDefault(size = 10, sort = "popularity", direction = Sort.Direction.DESC) Pageable merchandisePage
    ) {
        // Get paginated merchandise from repository
        List<MerchandiseOverviewDTO> emptyDTOs = Collections.nCopies(merchandisePage.getPageSize(), new MerchandiseOverviewDTO());
        Page<MerchandiseOverviewDTO> merchandiseDTOs = new PageImpl<>(emptyDTOs, merchandisePage, 0);

        return ResponseEntity.ok(merchandiseDTOs);
    }
}
