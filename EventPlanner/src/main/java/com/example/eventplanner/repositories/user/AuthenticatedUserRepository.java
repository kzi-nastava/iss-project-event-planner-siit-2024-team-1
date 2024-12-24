package com.example.eventplanner.repositories.user;

import com.example.eventplanner.model.user.Administrator;
import com.example.eventplanner.model.user.AuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticatedUserRepository extends JpaRepository<AuthenticatedUser, Integer> {
}
