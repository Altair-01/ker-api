package com.entreprise.immobilier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 👤 L’expéditeur du message */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    @NotNull(message = "L'expéditeur est obligatoire.")
    private User sender;

    /** 👥 Le destinataire du message */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    @NotNull(message = "Le destinataire est obligatoire.")
    private User receiver;

    /** 📨 Contenu du message */
    @NotBlank(message = "Le contenu du message ne peut pas être vide.")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    /** 🕒 Date d’envoi du message */
    @Column(nullable = false)
    private LocalDateTime date;

    /** ✅ Indique si le message a été lu ou non */
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    /** Définit la date automatiquement avant insertion */
    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + (sender != null ? sender.getId() : null) +
                ", receiverId=" + (receiver != null ? receiver.getId() : null) +
                ", isRead=" + isRead +
                ", date=" + date +
                '}';
    }
}
