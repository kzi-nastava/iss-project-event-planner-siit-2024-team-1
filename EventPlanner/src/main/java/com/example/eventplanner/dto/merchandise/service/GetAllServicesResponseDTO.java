package com.example.eventplanner.dto.merchandise.service;

import com.example.eventplanner.model.merchandise.Service;
import lombok.Data;

import java.util.List;

@Data
public class GetAllServicesResponseDTO {
    private List<Service> services;
}
