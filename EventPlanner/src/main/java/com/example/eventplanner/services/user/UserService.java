package com.example.eventplanner.services.user;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.user.*;
import com.example.eventplanner.dto.user.update.*;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.*;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.message.MessageRepository;
import com.example.eventplanner.repositories.user.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventOrganizerRepository eventOrganizerRepository;
    private final MessageRepository messageRepository;
    private final BusinessPhotoRepository businessPhotoRepository;
    private final AdministratorRepository administratorRepository;

    public GetAuByIdResponseDTO getAuById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GetAuByIdResponseDTO responseDTO = new GetAuByIdResponseDTO();
        responseDTO.setEmail(user.getUsername());
        return responseDTO;
    }

    public GetEoByIdResponseDTO getAdminById(int id){
        Administrator user = administratorRepository.findById(id)
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
        responseDTO.setPhotos(user.getPhotos().stream().map(this::mapToBusinnesPhotoDTO).toList());
        return responseDTO;
    }

    private BusinnesPhotoDTO mapToBusinnesPhotoDTO(BusinessPhoto photo){
        BusinnesPhotoDTO businnesPhotoDTO = new BusinnesPhotoDTO();
        businnesPhotoDTO.setId(photo.getId());
        businnesPhotoDTO.setPhoto(photo.getPhoto());
        return businnesPhotoDTO;
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
        user.setPhotos(businessPhotoRepository.findAllById(request.getPhotos()));


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




    public List<UserOverviewDTO> getChatUsers(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get all messages for this user
        List<Message> allMessages = messageRepository.findByRecipientIdOrSenderId(userId, userId);

        // Get unique user IDs from messages
        Set<Integer> chatUserIds = allMessages.stream()
                .map(message -> message.getSender().getId() == userId ?
                        message.getRecipient().getId() : message.getSender().getId())
                .collect(Collectors.toSet());

        // Map each user ID to their latest message time
        Map<Integer, LocalDateTime> latestMessageTimes = new HashMap<>();
        for (Integer chatUserId : chatUserIds) {
            LocalDateTime latestTime = allMessages.stream()
                    .filter(msg -> msg.getSender().getId() == chatUserId || msg.getRecipient().getId() == chatUserId)
                    .map(Message::getSentTIme)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.MIN);
            latestMessageTimes.put(chatUserId, latestTime);
        }

        // Convert to DTOs
        return latestMessageTimes.entrySet().stream()
                .map(entry -> {
                    User chatUser = userRepository.findById(entry.getKey())
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    if (user.getBlockedUsers().contains(chatUser)) {
                        return null;
                    }
                    return convertToUserOverviewDTO(chatUser);
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.<UserOverviewDTO, LocalDateTime>comparing(dto -> latestMessageTimes.get(dto.getId())).reversed())
                .collect(Collectors.toList());
    }

    private UserOverviewDTO convertToUserOverviewDTO(User user) {
        return new UserOverviewDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getPhoto(),user.getRole());
    }


    public BlockUserDTO blockUser(int blockerId, int blockedUserId) {
        // Validate the blocker and the blocked user
        if (blockerId == blockedUserId) {
            throw new RuntimeException("Users cannot block themselves.");
        }

        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Blocker user not found with ID: " + blockerId));
        User blockedUser = userRepository.findById(blockedUserId)
                .orElseThrow(() -> new RuntimeException("Blocked user not found with ID: " + blockedUserId));

        // Prevent re-blocking if already blocked
        if (blocker.getBlockedUsers().contains(blockedUser)) {
            throw new RuntimeException("User is already blocked.");
        }

        // Add the blocked user to the blocker's list of blocked users
        blocker.getBlockedUsers().add(blockedUser);
        userRepository.save(blocker); // Persist changes
        return new BlockUserDTO(blockerId, blockedUserId);
    }

    public List<UserOverviewDTO> getBlockedUsers(int blockerId) {
        User blocker = userRepository.findById(blockerId)
                .orElseThrow(() -> new RuntimeException("Blocker user not found with ID: " + blockerId));
        return blocker.getBlockedUsers().stream().map(this::convertToUserOverviewDTO).collect(Collectors.toList());
    }

    public UserOverviewDTO getMessagedChatUser(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("user not found with id: " + id)
        );

        return convertToUserOverviewDTO(user);
    }
}
