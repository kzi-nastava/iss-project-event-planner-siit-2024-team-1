package com.example.eventplanner.controllers.user;

import com.example.eventplanner.dto.event.EventOverviewDTO;
//import com.example.eventplanner.dto.event.FollowEventResponseDTO;
import com.example.eventplanner.dto.event.FollowEventResponseDTO;
import com.example.eventplanner.dto.merchandise.FavoriteResponseDTO;
import com.example.eventplanner.dto.user.GetAuByIdResponseDTO;
import com.example.eventplanner.dto.user.GetEoByIdResponseDTO;
import com.example.eventplanner.dto.user.GetSpByIdResponseDTO;
import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.dto.user.update.*;
import com.example.eventplanner.services.event.EventService;
import com.example.eventplanner.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<GetAuByIdResponseDTO> getAuById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(userService.getAuById(id));
    }

    @GetMapping("/sp/{id}")
    public ResponseEntity<GetSpByIdResponseDTO> getSpById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(userService.getSpById(id));
    }

    @GetMapping("/eo/{id}")
    public ResponseEntity<GetEoByIdResponseDTO> getEoById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(userService.getEoById(id));
    }

    @PutMapping("/{userId}/favorite-events/{eventId}")
    public ResponseEntity<EventOverviewDTO> addEventToFavorites(@PathVariable int userId, @PathVariable int eventId) {
        return ResponseEntity.ok(userService.addEventToFavorites(userId, eventId));
    }

    @PutMapping("/update-au/{id}")
    public ResponseEntity<UpdateAuResponseDTO> update(@PathVariable int id, @RequestBody UpdateAuRequestDTO user) {
        return ResponseEntity.ok(new UpdateAuResponseDTO());
    }

    @PutMapping("/update-eo/{id}")
    public ResponseEntity<UpdateEoResponseDTO> updateEo(@PathVariable int id, @RequestBody UpdateEoRequestDTO user) {
        return ResponseEntity.ok(userService.updateEo(id, user));
    }

    @PutMapping("/update-sp/{id}")
    public ResponseEntity<UpdateSpResponseDTO> updateSp(@PathVariable int id, @RequestBody UpdateSpRequestDTO user) {
        return ResponseEntity.ok(userService.updateSp(id, user));
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<ChangePasswordResponseDTO> changePassword(@PathVariable int id, @RequestBody ChangePasswordRequestDTO dto) {
        return ResponseEntity.ok(userService.changePassword(id, dto));
    }

    @PostMapping("/favorite-merchandise/{id}")
    public ResponseEntity<FavoriteResponseDTO> addToFavorites(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new FavoriteResponseDTO());
    }

    @PostMapping("/follow-event")
    public ResponseEntity<FollowEventResponseDTO> followEvent(
            @RequestParam int userId,
            @RequestParam int eventId
    ) {
        userService.followEvent(userId,eventId);
        return ResponseEntity.ok(new FollowEventResponseDTO(userId,eventId));
    }
}
