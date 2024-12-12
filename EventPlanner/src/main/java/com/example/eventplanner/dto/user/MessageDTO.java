package com.example.eventplanner.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private int id;
    private String content;
    private LocalDateTime sentTime;
    private int senderId;      // Only store sender's ID instead of the entire User object
    private int recipientId;   // Only store recipient's ID instead of the entire User object
}
