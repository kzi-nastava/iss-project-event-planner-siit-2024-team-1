package com.example.eventplanner.controllers.auth;

import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.model.auth.AuthenticationResponse;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class    AuthenticationController {

    private final AuthenticationService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }


//    @PostMapping("/register-au")
//    public ResponseEntity<AuthenticationResponse> registerAu(
//            @RequestBody RegisterAuUserRequestDTO request
//            ) {
//        return ResponseEntity.ok(authService.registerAu(request));
//    }

    @PostMapping("/register-eo")
    public ResponseEntity<RegisterEoRequestResponseDTO> registerEo(
            @RequestBody RegisterEoRequestDTO request
    ) {
        return ResponseEntity.ok(authService.registerEo(request));
    }

    @PostMapping("/register-sp")
    public ResponseEntity<RegisterSpRequestResponseDTO> registerSp(
            @RequestBody RegisterSpRequestDTO request
    ) {
        return ResponseEntity.ok(authService.registerSp(request));
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
