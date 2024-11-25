package com.example.eventplanner.dto.merchandise;

import com.example.eventplanner.model.merchandise.Merchandise;
import lombok.Data;

import java.util.List;

@Data
public class FavoriteResponseDTO {
    private List<Merchandise> favoriteMerchandises;
}
