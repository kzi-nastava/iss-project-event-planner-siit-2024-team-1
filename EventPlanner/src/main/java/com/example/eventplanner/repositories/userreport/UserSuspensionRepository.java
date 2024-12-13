package com.example.eventplanner.repositories.userreport;

import com.example.eventplanner.model.user.User;
import com.example.eventplanner.model.user.UserSuspension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSuspensionRepository extends JpaRepository<UserSuspension, Integer> {
    Optional<UserSuspension> findByUserAndStartTimeBeforeAndEndTimeAfter(User user, LocalDateTime now1, LocalDateTime now2);}
