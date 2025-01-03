package com.example.eventplanner.controllers.auth;

import com.example.eventplanner.dto.event.InviteResponseDTO;
import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.model.auth.AuthenticationResponse;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class    AuthenticationController {

    private final AuthenticationService authService;
    private final PasswordEncoder passwordEncoder;


//    @PostMapping("/register-au")
//    public ResponseEntity<AuthenticationResponse> registerAu(
//            @RequestBody RegisterAuUserRequestDTO request
//            ) {
//        return ResponseEntity.ok(authService.registerAu(request));
//    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(@RequestParam("token") String token) {
        try {
            String message = authService.verifyUser(token);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register-eo")
    public ResponseEntity<RegisterEoRequestResponseDTO> registerEo(
            @RequestBody RegisterEoRequestDTO request, @RequestParam(defaultValue = "false") boolean promotion
    ) {
        return ResponseEntity.ok(authService.registerEo(request, promotion));
    }

    @PostMapping("/register-sp")
    public ResponseEntity<RegisterSpRequestResponseDTO> registerSp(
            @RequestBody RegisterSpRequestDTO request, @RequestParam(defaultValue = "false") boolean promotion
    ) {
        return ResponseEntity.ok(authService.registerSp(request, promotion));
    }

    @PostMapping("/fast-register")
    public ResponseEntity<LoginResponseDTO> fastRegister(
            @RequestParam String email
    ) {
        return ResponseEntity.ok(authService.fastRegister(email));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Boolean> deactivate(
            @PathVariable int id
    ) {
        return ResponseEntity.ok(authService.deactivate(id));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }
}
