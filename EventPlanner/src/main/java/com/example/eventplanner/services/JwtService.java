package com.example.eventplanner.services;

import com.example.eventplanner.dto.event.InviteResponseDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.auth.TokenRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.services.email.EmailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    @Value("${application.front.address.login}")
    private String frontLoginAddress;

    @Value("${application.front.address.fast-register}")
    private String frontFastRegisterAddress;


    private final TokenRepository tokenRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = tokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);

        boolean validRefreshToken = tokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire);
    }

    private String generateToken(User user, long expireTime) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    public InviteResponseDTO inviteToEvent(int eventId, String userEmail) {
        Optional<User> user = userRepository.findByUsername(userEmail);
        String token=genereateEventToken(eventId, userEmail);
        if (user.isPresent()) {
            emailService.sendMail("system@eventplanner.com", userEmail, "Event invite link", "<a href=" + frontLoginAddress + "?inviteToken=" + token + ">Web application link</a>");
        }
        //send correct email
        else {
            emailService.sendMail("system@eventplanner.com", userEmail, "Event invite link", "<a href=" + frontFastRegisterAddress+ "?inviteToken=" + token + ">Web application link</a>");
        }

        return new InviteResponseDTO(token);
    }

    public String genereateEventToken(int eventId, String userEmail) {
        long expireTime = accessTokenExpire;
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        String token = Jwts
                .builder()
                .subject(event.getTitle())
                .claim("id", event.getId())
                .claim("title", event.getTitle())
                .claim("userEmail", userEmail)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateActivationToken(User user, long activationTokenExpire) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + activationTokenExpire))
                .signWith(getSigninKey())
                .compact();
    }
}
