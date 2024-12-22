package com.example.eventplanner.controllers.message;

import com.example.eventplanner.dto.user.MessageDTO;
import com.example.eventplanner.dto.user.MessageRequestDTO;
import com.example.eventplanner.model.user.Message;
import com.example.eventplanner.services.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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

    @MessageMapping("/chat.sendMessage")
    @SendTo("/user/message")
    public MessageDTO sendMessage(@Payload MessageRequestDTO messageDTO) {
        MessageDTO message = messageService
                .sendMessageByIds(messageDTO.getSenderId(), messageDTO.getReceiverId(), messageDTO.getContent());
        return message;
    }
}