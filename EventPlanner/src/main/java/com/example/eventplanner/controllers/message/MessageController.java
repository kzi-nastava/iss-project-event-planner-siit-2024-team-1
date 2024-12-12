package com.example.eventplanner.controllers.message;

import com.example.eventplanner.dto.user.MessageDTO;
import com.example.eventplanner.model.user.Message;
import com.example.eventplanner.services.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

    private final MessageService messageService;

    /**
     * Endpoint to fetch all messages between a sender and recipient, sorted from oldest to newest.
     *
     * @param senderId   The ID of the sender.
     * @param recipientId The ID of the recipient.
     * @return List of messages, sorted by sent time.
     */
    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessagesBySenderAndRecipient(
            @RequestParam int senderId,
            @RequestParam int recipientId
    ) {
        List<MessageDTO> messages = messageService.getMessagesBySenderAndRecipientSorted(senderId, recipientId);
        return ResponseEntity.ok(messages);
    }

    /**
     * Endpoint to send a message from a sender to a recipient.
     *
     * @param senderId   The ID of the sender.
     * @param recipientId The ID of the recipient.
     * @param content     The content of the message.
     * @return The newly created and saved message.
     */
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(
            @RequestParam int senderId,
            @RequestParam int recipientId,
            @RequestParam String content
    ) {
        MessageDTO message = messageService.sendMessageByIds(senderId, recipientId, content);
        return ResponseEntity.ok(message);
    }
}