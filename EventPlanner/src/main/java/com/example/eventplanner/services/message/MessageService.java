package com.example.eventplanner.services.message;

import com.example.eventplanner.dto.user.MessageDTO;
import com.example.eventplanner.model.user.Message;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.message.MessageRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<MessageDTO> getMessagesBySenderAndRecipientSorted(int senderId, int recipientId) {
        List<Message> messages = new ArrayList<>();

        // Add messages from sender to recipient
        messages.addAll(messageRepository.findBySenderIdAndRecipientId(senderId, recipientId));

        // Add messages from recipient to sender
        messages.addAll(messageRepository.findBySenderIdAndRecipientId(recipientId, senderId));

        // Sort the merged list by sent time
        return messages.stream()
                .sorted(Comparator.comparing(Message::getSentTIme)) // Sorting by sent time
                .map(this::convertToMessageDTO) // Mapping Message to MessageDTO
                .toList();
    }

    public MessageDTO sendMessageByIds(int senderId, int recipientId, String content) {
        // Fetch sender and recipient
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);

        if (sender.isEmpty() || recipient.isEmpty()) {
            throw new IllegalArgumentException("Sender or recipient ID is invalid.");
        }

        // Create and save the message
        Message message = new Message();
        message.setSender(sender.get());
        message.setRecipient(recipient.get());
        message.setContent(content);
        message.setSentTIme(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        // Return the saved message as a DTO
        return convertToMessageDTO(savedMessage);
    }

    // Utility method to convert Message to MessageDTO
    private MessageDTO convertToMessageDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getSentTIme(),
                message.getSender().getId(),
                message.getRecipient().getId()
        );
    }
}