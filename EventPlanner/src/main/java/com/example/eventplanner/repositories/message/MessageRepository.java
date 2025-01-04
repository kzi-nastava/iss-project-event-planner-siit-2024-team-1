package com.example.eventplanner.repositories.message;

import com.example.eventplanner.model.user.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderIdAndRecipientId(int senderId, int recipientId);

    List<Message> findByRecipientId(int organizerId);
    List<Message> findBySenderId(int senderId);
    List<Message> findByRecipientIdOrSenderId(int receiverId,int senderId);

}
