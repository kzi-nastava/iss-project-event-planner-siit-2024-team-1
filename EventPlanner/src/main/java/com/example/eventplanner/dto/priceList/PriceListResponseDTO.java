package com.example.eventplanner.dto.priceList;

import lombok.Data;

import java.util.List;

@Data
public class PriceListResponseDTO {
    private List<PriceListItemResponseDTO> priceListItems;
}
