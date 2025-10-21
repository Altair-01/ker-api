package com.entreprise.immobilier.controller;

import com.entreprise.immobilier.dto.MessageDTO;
import com.entreprise.immobilier.model.Message;
import com.entreprise.immobilier.service.interfaces.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Message>> getMessagesSent(@PathVariable Long senderId) {
        return ResponseEntity.ok(messageService.getMessagesSentByUser(senderId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<Message>> getMessagesReceived(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getMessagesReceivedByUser(receiverId));
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        return ResponseEntity.ok(messageService.getConversation(senderId, receiverId));
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getUnreadMessages(receiverId));
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody MessageDTO dto) {
        return ResponseEntity.ok(messageService.sendMessage(dto));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Message> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
