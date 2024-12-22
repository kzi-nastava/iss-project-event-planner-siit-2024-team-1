package com.example.eventplanner.services.user;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.user.GetAuByIdResponseDTO;
import com.example.eventplanner.dto.user.GetEoByIdResponseDTO;
import com.example.eventplanner.dto.user.GetSpByIdResponseDTO;
import com.example.eventplanner.dto.user.UserOverviewDTO;
import com.example.eventplanner.dto.user.auth.RegisterEoRequestDTO;
import com.example.eventplanner.dto.user.auth.RegisterEoRequestResponseDTO;
import com.example.eventplanner.dto.user.auth.RegisterSpRequestDTO;
import com.example.eventplanner.dto.user.auth.RegisterSpRequestResponseDTO;
import com.example.eventplanner.dto.user.update.*;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.user.*;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.message.MessageRepository;
import com.example.eventplanner.repositories.user.EventOrganizerRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventOrganizerRepository eventOrganizerRepository;
    private final MessageRepository messageRepository;

    public GetAuByIdResponseDTO getAuById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GetAuByIdResponseDTO responseDTO = new GetAuByIdResponseDTO();
        responseDTO.setEmail(user.getUsername());
        responseDTO.setName(user.getName());
        responseDTO.setSurname(user.getSurname());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setPhoto(user.getPhoto());
        return responseDTO;
    }

    public GetEoByIdResponseDTO getEoById(int id){
        User user = eventOrganizerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GetEoByIdResponseDTO responseDTO = new GetEoByIdResponseDTO();
        responseDTO.setEmail(user.getUsername());
        responseDTO.setName(user.getName());
        responseDTO.setSurname(user.getSurname());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setPhoto(user.getPhoto());
        return responseDTO;
    }

    public GetSpByIdResponseDTO getSpById(int id){
        ServiceProvider user = serviceProviderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GetSpByIdResponseDTO responseDTO = new GetSpByIdResponseDTO();
        responseDTO.setEmail(user.getUsername());
        responseDTO.setName(user.getName());
        responseDTO.setSurname(user.getSurname());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setPhoto(user.getPhoto());

        responseDTO.setCompany(user.getCompany());
        responseDTO.setDescription(user.getDescription());
        return responseDTO;
    }

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

        EventOrganizer user = eventOrganizerRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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

        ServiceProvider user =  serviceProviderRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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



    // Optional: Method to add an event to followed events
    public void followEvent(int userId, int eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Check if the event is not already followed
       Event event = eventRepository.findById(eventId)
               .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));
        if (!user.getFollowedEvents().contains(event)) {
            user.getFollowedEvents().add(event);
            userRepository.save(user);
        }
    }

    public List<UserOverviewDTO> getServiceProvidersForOrganizerEvents(int organizerId) {
        // Fetch the Event Organizer by ID
        EventOrganizer organizer = eventOrganizerRepository.findById(organizerId)
                .orElseThrow(() -> new EntityNotFoundException("Event Organizer not found with id: " + organizerId));

        // Fetch all events organized by the Event Organizer
        List<Event> organizedEvents = organizer.getOrganizingEvents();

        // Fetch unique Service Providers for Merchandise in the events' budgets
        List<UserOverviewDTO> serviceProviders = organizedEvents.stream()
                .flatMap(event -> event.getMerchandise().stream()) // Get merchandise from each event
                .distinct() // Avoid duplicate merchandise
                .map(merchandise -> serviceProviderRepository.findByMerchandiseId(merchandise.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Service Provider not found for merchandise id: " + merchandise.getId())))
                .distinct() // Avoid duplicate service providers
                .map(this::convertToUserOverviewDTO) // Convert to DTO
                .toList();

        return serviceProviders;
    }

    public List<UserOverviewDTO> getAuWhoMessagedEo(int organizerId) {
        List<Message> messages = messageRepository.findByRecipientId(organizerId);

        // Extract senders who are AuthenticatedUsers
        return messages.stream()
                .map(Message::getSender) // Get the sender of the message
                .filter(user -> user instanceof AuthenticatedUser) // Keep only AuthenticatedUsers
                .distinct() // Remove duplicates
                .map(this::convertToUserOverviewDTO) // Convert to UserOverviewDTO
                .toList(); // Collect the result as a list
    }

    public List<UserOverviewDTO> getChatUsersForEo(int organizerId) {
        // Fetch users who are authenticated and messaged the organizer
        List<UserOverviewDTO> users = new ArrayList<>(getAuWhoMessagedEo(organizerId));

        // Fetch service providers for the organizer's events and add them to the list
        users.addAll(getServiceProvidersForOrganizerEvents(organizerId));

        return users;
    }

    public List<UserOverviewDTO> getChatUsersForAu(){
        return eventOrganizerRepository.findAll().stream().map(this::convertToUserOverviewDTO).toList();
    }

    public List<UserOverviewDTO> getEoWhoMessagedSp(int serviceProviderId) {
        // Fetch the Service Provider by ID
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new RuntimeException("Service Provider not found with id: " + serviceProviderId));

        // Fetch all messages where the recipient is the service provider
        List<Message> messages = messageRepository.findByRecipientId(serviceProviderId);

        // Extract senders who are EventOrganizers
        return messages.stream()
                .map(Message::getSender) // Get the sender of the message
                .filter(user -> user instanceof EventOrganizer) // Keep only EventOrganizers
                .distinct() // Remove duplicates
                .map(this::convertToUserOverviewDTO) // Convert to UserOverviewDTO
                .toList(); // Collect the result as a list
    }

    private UserOverviewDTO convertToUserOverviewDTO(User user) {
        return new UserOverviewDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getPhoto());
    }

    private UserOverviewDTO  convertToUserOverviewDTO(ServiceProvider sp){
        return new UserOverviewDTO(sp.getId(),sp.getUsername(),sp.getName(),sp.getSurname(),sp.getPhoto());
    }


    public UserOverviewDTO getMessagedSp(int id) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Service Provider not found with id: " + id)
        );

        return convertToUserOverviewDTO(serviceProvider);
    }
}
