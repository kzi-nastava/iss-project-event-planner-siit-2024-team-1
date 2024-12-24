package com.example.eventplanner.controllers.message;

import com.example.eventplanner.dto.user.MessageDTO;
import com.example.eventplanner.dto.user.MessageRequestDTO;
import com.example.eventplanner.model.user.Message;
import com.example.eventplanner.services.message.MessageService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate messagingTemplate;
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
    public MessageDTO sendMessage(@Payload MessageRequestDTO messageDTO) {
        MessageDTO message = messageService
                .sendMessageByIds(messageDTO.getSenderId(), messageDTO.getReceiverId(), messageDTO.getContent());

        String conversationId = generateConversationId(message.getSenderId(), message.getRecipientId());
        System.out.println(conversationId);
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getSenderId()),
                                                    "/private/messages/" + conversationId, message);
        messagingTemplate.convertAndSendToUser(String.valueOf(message.getRecipientId()),
                                                    "/private/messages/" + conversationId, message);
        return message;
    }


    private String generateConversationId(int senderId, int recipientId) {
        //function sorts ids, so that conversation is unique no matter which user sends the message
        return senderId < recipientId ? senderId + "-" + recipientId : recipientId + "-" + senderId;
    }
}