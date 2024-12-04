package com.example.eventplanner.services.user;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.user.auth.RegisterEoRequestDTO;
import com.example.eventplanner.dto.user.auth.RegisterEoRequestResponseDTO;
import com.example.eventplanner.dto.user.auth.RegisterSpRequestDTO;
import com.example.eventplanner.dto.user.auth.RegisterSpRequestResponseDTO;
import com.example.eventplanner.dto.user.update.*;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final PasswordEncoder passwordEncoder;

    public EventOverviewDTO addEventToFavorites(int userId, int eventId) {
        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Check if the event is already in the user's favorite events
        if (!user.getFavoriteEvents().contains(event)) {
            user.getFavoriteEvents().add(event); // Add the event to the list
            userRepository.save(user); // Persist changes

            return convertToOverviewDTO(event);
        } else {
            throw new RuntimeException("Event is already in the user's favorites");
        }
    }

    private EventOverviewDTO convertToOverviewDTO(Event event) {
        EventOverviewDTO dto = new EventOverviewDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setDate(event.getDate());
        dto.setAddress(event.getAddress());
        dto.setType(event.getType() != null ? event.getType().getTitle() : null);
        return dto;
    }

    public UpdateEoResponseDTO updateEo(int id, UpdateEoRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(!userRepository.findById(id).isPresent()) {
            return new UpdateEoResponseDTO(id, "User Doesn't Exist", null,null, null,null, null);
        }

        EventOrganizer user = new EventOrganizer();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(mapToAddress(request.getAddress()));
        user.setPhoto(request.getPhoto());

        user = userRepository.save(user);

        return new UpdateEoResponseDTO(user.getId(), "User Updated Successfully", user.getName(), user.getSurname(), user.getPhoneNumber(),request.getAddress(), user.getPhoto());

    }

    public Address mapToAddress(AddressDTO dto){
        Address address = new Address();

        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());

        return address;
    }

    public UpdateSpResponseDTO updateSp(int id, UpdateSpRequestDTO request) {

        // check if user already exist. if exist than authenticate the user
        if(!userRepository.findById(id).isPresent()) {
            return new UpdateSpResponseDTO(id, "User Doesn't Exist", null,null, null,null, null, null,null);
        }

        ServiceProvider user = serviceProviderRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(mapToAddress(request.getAddress()));
        user.setPhoto(request.getPhoto());

        user.setDescription(request.getDescription());
        //Auser.setPhotos(request.getPhotos());


        user = userRepository.save(user);

        return new UpdateSpResponseDTO(user.getId(), "User Updated Successfully", user.getName(), user.getSurname(), user.getPhoneNumber(),request.getAddress(), user.getPhoto(), user.getDescription(), null);

    }

    public ChangePasswordResponseDTO changePassword(int id, ChangePasswordRequestDTO request) {
        ChangePasswordResponseDTO responseDTO = new ChangePasswordResponseDTO();
        // check if user already exist. if exist than authenticate the user
        if(!userRepository.findById(id).isPresent()) {
            responseDTO.setMessage("User Doesn't Exist");
            return responseDTO;
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));


        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            responseDTO.setMessage("Incorrect Old Password");
            return responseDTO;
        }

        if(!request.getNewPassword1().equals(request.getNewPassword2())) {
            responseDTO.setMessage("New passwords do not match");
            return responseDTO;
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword1()));

        user = userRepository.save(user);

        responseDTO.setMessage("Password Updated Successfully");

        return responseDTO;
    }
}
