package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.MessageDTO;
import com.entreprise.immobilier.model.Message;
import com.entreprise.immobilier.model.User;
import com.entreprise.immobilier.repository.MessageRepository;
import com.entreprise.immobilier.repository.UserRepository;
import com.entreprise.immobilier.service.interfaces.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message sendMessage(MessageDTO dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Expéditeur introuvable."));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Destinataire introuvable."));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .message(dto.getMessage())
                .isRead(false)
                .date(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    @Override
    public Message markAsRead(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message introuvable."));
        message.setRead(true);
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new EntityNotFoundException("Message introuvable.");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getMessagesSentByUser(Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Expéditeur introuvable."));
        return messageRepository.findBySender(sender);
    }

    @Override
    public List<Message> getMessagesReceivedByUser(Long receiverId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Destinataire introuvable."));
        return messageRepository.findByReceiver(receiver);
    }

    @Override
    public List<Message> getConversation(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Expéditeur introuvable."));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Destinataire introuvable."));
        return messageRepository.findBySenderAndReceiver(sender, receiver);
    }

    @Override
    public List<Message> getUnreadMessages(Long receiverId) {
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Destinataire introuvable."));
        return messageRepository.findByReceiverAndIsReadFalse(receiver);
    }
}
