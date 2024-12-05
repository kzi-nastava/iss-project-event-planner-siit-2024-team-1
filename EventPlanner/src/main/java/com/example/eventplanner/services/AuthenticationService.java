package com.example.eventplanner.services;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.user.auth.*;
import com.example.eventplanner.model.auth.AuthenticationResponse;
import com.example.eventplanner.model.auth.Token;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.user.BusinessPhoto;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.auth.TokenRepository;
import com.example.eventplanner.repositories.user.EventOrganizerRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.services.email.EmailService;
import com.example.eventplanner.services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;


//    public RegisterAuUserResponseRequestDTO registerAu(RegisterAuUserRequestDTO request) {
//
//        // check if user already exist. if exist than authenticate the user
//        if(repository.findByUsername(request.getUsername()).isPresent()) {
//            return new RegisterAuUserResponseRequestDTO(null, null,"User already exist");
//        }
//
//        User user = new User();
//        user.setName(request.getName());
//        user.setSurname(request.getSurname());
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//
//        user.setRole(request.getRole());
//
//        user = repository.save(user);
//
//        String accessToken = jwtService.generateAccessToken(user);
//        String refreshToken = jwtService.generateRefreshToken(user);
//
//        saveUserToken(accessToken, refreshToken, user);
//
//        return new RegisterAuUserResponseRequestDTO(accessToken, refreshToken,"User registration was successful");
//
//    }

    public RegisterEoRequestResponseDTO registerEo(RegisterEoRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getEmail()).isPresent()) {
            return new RegisterEoRequestResponseDTO(-1, "User Already Exists", null,null, null,null, null,null, null, null);
        }

        EventOrganizer user = new EventOrganizer();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(mapToAddress(request.getAddress()));
        user.setPhoto(request.getPhoto());
        user.setRole(request.getRole());
        user.setAuthorities("ahahah");

        long activationTokenExpire = 24 * 60 * 60 * 1000;
        String activationToken = jwtService.generateActivationToken(user, activationTokenExpire);
        user.setActivationToken(activationToken);
        user.setTokenExpiration(new Date(System.currentTimeMillis() + activationTokenExpire));
        user.setVerified(false);

        user = repository.save(user);

        sendActivationEmail(user.getUsername(), activationToken);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new RegisterEoRequestResponseDTO(user.getId(), "User Created Successfully", user.getName(), user.getSurname(), user.getPhoneNumber(),request.getAddress(), user.getUsername(), user.getPhoto(), accessToken, refreshToken);

    }

    public String verifyUser(String activationToken) {
        // Extract the username from the token
        String username = jwtService.extractUsername(activationToken);

        // Validate the token and retrieve the user
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        // Check if the token matches and has not expired
        if (!user.getActivationToken().equals(activationToken) ||
                user.getTokenExpiration().before(new Date())) {
            throw new RuntimeException("Invalid or expired activation token");
        }

        // Update user status to verified
        user.setVerified(true);
        user.setActivationToken(null); // Clear the activation token
        user.setTokenExpiration(null); // Clear the token expiration
        repository.save(user);

        return "Account verified successfully!";
    }

    public Address mapToAddress(AddressDTO dto){
        Address address = new Address();

        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());

        return address;
    }

    public RegisterSpRequestResponseDTO registerSp(RegisterSpRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getEmail()).isPresent()) {
            return new RegisterSpRequestResponseDTO(-1, "User Already Exists", null,null, null,null, null, null,null, null, null, null, null);
        }

        ServiceProvider user = new ServiceProvider();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(mapToAddress(request.getAddress()));
        user.setPhoto(request.getPhoto());
        user.setRole(request.getRole());
        user.setAuthorities("ahahah");

        user.setCompany(request.getCompany());
        user.setDescription(request.getDescription());


        long activationTokenExpire = 24 * 60 * 60 * 1000;
        String activationToken = jwtService.generateActivationToken(user, activationTokenExpire);
        user.setActivationToken(activationToken);
        user.setTokenExpiration(new Date(System.currentTimeMillis() + activationTokenExpire));
        user.setVerified(false);

        user = repository.save(user);

        sendActivationEmail(user.getUsername(), activationToken);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new RegisterSpRequestResponseDTO(user.getId(), "User Created Successfully", user.getName(), user.getSurname(), user.getPhoneNumber(),request.getAddress(), user.getUsername(), user.getPhoto(), user.getCompany(),user.getDescription(), null, accessToken, refreshToken);

    }

    private void sendActivationEmail(String email, String token) {
        String activationLink = "http://localhost:8080/api/auth/activate?token=" + token;
        emailService.sendMail(
                "system@eventplanner.com",
                email,
                "Activate Your Account",
                "Click the link to activate your account: " + activationLink +
                        ". The link will expire in 24 hours."
        );
    }

    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getEmail()).orElseThrow();

        if (!user.isVerified()) {
            throw new RuntimeException("Account not verified. Please verify your email.");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);

        saveUserToken(accessToken, refreshToken, user);

        return new LoginResponseDTO(user.getUsername(), accessToken, refreshToken);

    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }  

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String username = jwtService.extractUsername(token);

        // check if the user exist in database
        User user = repository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
