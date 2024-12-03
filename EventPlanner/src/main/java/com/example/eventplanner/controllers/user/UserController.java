package com.example.eventplanner.controllers.user;

import com.example.eventplanner.dto.merchandise.FavoriteResponseDTO;
import com.example.eventplanner.dto.user.GetAuByIdResponseDTO;
import com.example.eventplanner.dto.user.GetEoByIdResponseDTO;
import com.example.eventplanner.dto.user.GetSpByIdResponseDTO;
import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.dto.user.update.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<GetAuByIdResponseDTO> getAuById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new GetAuByIdResponseDTO());
    }

    @GetMapping("/sp/{id}")
    public ResponseEntity<GetSpByIdResponseDTO> getSpById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new GetSpByIdResponseDTO());
    }

    @GetMapping("/eo/{id}")
    public ResponseEntity<GetEoByIdResponseDTO> getEoById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new GetEoByIdResponseDTO());
    }



//    @PostMapping("/register")
//    public ResponseEntity<RegisterAuUserResponseRequestDTO> register(@RequestBody RegisterAuUserRequestDTO user) {
//        return ResponseEntity.ok(new RegisterAuUserResponseRequestDTO());
//    }
//
//    @PostMapping("/register-eo")
//    public ResponseEntity<RegisterEoRequestResponseDTO> registerEo(@RequestBody RegisterEoRequestDTO user) {
//        return ResponseEntity.ok(new RegisterEoRequestResponseDTO());
//    }
//
//    @PostMapping("/register-sp")
//    public ResponseEntity<RegisterSpRequestResponseDTO> registerSp(@RequestBody RegisterSpRequestDTO user) {
//        return ResponseEntity.ok(new RegisterSpRequestResponseDTO());
//    }

    @PutMapping("/{userId}/favorite-events/{eventId}")
    public ResponseEntity<UpdateAuResponseDTO> addEventToFavorites(@PathVariable int userId, @PathVariable int eventId) {
        return ResponseEntity.ok(new UpdateAuResponseDTO());
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateAuResponseDTO> update(@RequestBody UpdateAuRequestDTO user) {
        return ResponseEntity.ok(new UpdateAuResponseDTO());
    }

    @PutMapping("/update-eo")
    public ResponseEntity<UpdateEoResponseDTO> updateEo(@RequestBody UpdateEoRequestDTO user) {
        return ResponseEntity.ok(new UpdateEoResponseDTO());
    }

    @PutMapping("/update-sp")
    public ResponseEntity<UpdateSpResponseDTO> updateSp(@RequestBody UpdateSpRequestDTO user) {
        return ResponseEntity.ok(new UpdateSpResponseDTO());
    }

    @PostMapping("/favorite-merchandise/{id}")
    public ResponseEntity<FavoriteResponseDTO> addToFavorites(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new FavoriteResponseDTO());
    }
}
