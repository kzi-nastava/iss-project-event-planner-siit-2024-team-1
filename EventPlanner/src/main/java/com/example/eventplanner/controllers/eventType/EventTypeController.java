package com.example.eventplanner.controllers.eventType;

import com.example.eventplanner.dto.eventType.GetAllEventTypesResponseDTO;
import com.example.eventplanner.dto.eventType.create.CreateEventTypeRequestDTO;
import com.example.eventplanner.dto.eventType.create.CreateEventTypeResponseDTO;
import com.example.eventplanner.dto.eventType.update.UpdateEventTypeRequestDTO;
import com.example.eventplanner.dto.eventType.update.UpdateEventTypeResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/eventTypes")
public class EventTypeController {

    @GetMapping
    public GetAllEventTypesResponseDTO GetAll(){
        return new GetAllEventTypesResponseDTO();
    }

    @PostMapping()
    public CreateEventTypeResponseDTO Create(@RequestBody CreateEventTypeRequestDTO request) {
        return new CreateEventTypeResponseDTO();
    }

    @PutMapping()
    public UpdateEventTypeResponseDTO Update(@RequestBody UpdateEventTypeRequestDTO request) {
        return new UpdateEventTypeResponseDTO();
    }
}
