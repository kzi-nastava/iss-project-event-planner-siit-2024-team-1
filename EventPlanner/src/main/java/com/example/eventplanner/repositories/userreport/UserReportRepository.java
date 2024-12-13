package com.example.eventplanner.repositories.userreport;

import com.example.eventplanner.model.user.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Integer> {
}
