package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.user.BusinessPhoto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetSpByIdResponseDTO {
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;
    private String photo;

    private String company;
    private String description;
    private List<BusinnesPhotoDTO> photos;
}
