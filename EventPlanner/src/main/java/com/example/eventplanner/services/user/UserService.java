package com.example.eventplanner.services.user;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

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
}
