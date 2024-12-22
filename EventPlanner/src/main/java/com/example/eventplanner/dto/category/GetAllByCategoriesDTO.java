package com.example.eventplanner.dto.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetAllByCategoriesDTO {
    private List<Integer> categories;
}
