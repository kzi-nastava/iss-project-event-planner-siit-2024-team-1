package com.example.eventplanner.repositories.message;

import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.Review;
import com.example.eventplanner.model.user.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderIdAndRecipientId(int senderId, int recipientId);

    List<Message> findByRecipientId(int organizerId);

}
