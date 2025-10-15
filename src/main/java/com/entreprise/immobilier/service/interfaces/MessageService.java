package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.MessageDTO;
import com.entreprise.immobilier.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> getAllMessages();

    Optional<Message> getMessageById(Long id);

    Message sendMessage(MessageDTO dto);

    Message markAsRead(Long id);

    void deleteMessage(Long id);

    List<Message> getMessagesSentByUser(Long senderId);

    List<Message> getMessagesReceivedByUser(Long receiverId);

    List<Message> getConversation(Long senderId, Long receiverId);

    List<Message> getUnreadMessages(Long receiverId);
}
