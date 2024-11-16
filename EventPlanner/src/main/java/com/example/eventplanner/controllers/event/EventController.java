package com.example.eventplanner.controllers.event;

import com.example.eventplanner.dto.event.GetAllEventsResponseDTO;
import com.example.eventplanner.dto.event.GetEventByIdResponseDTO;
import com.example.eventplanner.dto.event.GetEventReportResponseDTO;
import com.example.eventplanner.dto.event.create.CreateActivityRequestDTO;
import com.example.eventplanner.dto.event.create.CreateActivityResponseDTO;
import com.example.eventplanner.dto.event.create.CreateEventResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/events")
public class EventController {

    @GetMapping()
    public GetAllEventsResponseDTO GetAll(){
        return new GetAllEventsResponseDTO();
    }

    @GetMapping("/{id}")
    public GetEventByIdResponseDTO GetById(@PathVariable( value = "id") int id){
        return new GetEventByIdResponseDTO();
    }

    @GetMapping("events/report/{id}")
    public GetEventReportResponseDTO GetReport(@PathVariable( value = "id") int id){
        return new GetEventReportResponseDTO();
    }

    @PostMapping()
    public CreateEventResponseDTO Create(@RequestBody CreateEventResponseDTO request) {
        return new CreateEventResponseDTO();
    }

    @PostMapping("/{id}")
    public String AddToFavorites(@PathVariable( value = "id") int id){
        return  "success";
    }

    @PostMapping("/activity")
    public CreateActivityResponseDTO Create(@RequestBody CreateActivityRequestDTO request){
        return  new CreateActivityResponseDTO();
    }

}

