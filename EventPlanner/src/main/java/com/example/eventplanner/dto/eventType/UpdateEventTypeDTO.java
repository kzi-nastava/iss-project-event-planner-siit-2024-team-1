package com.example.eventplanner.dto.eventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateEventTypeDTO {
    private String description;
    private boolean active;
    private List<Integer> recommendedCategoryIds;

}
