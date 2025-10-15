package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Message;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);

    List<Message> findBySenderAndReceiver(User sender, User receiver);

    List<Message> findByReceiverAndIsReadFalse(User receiver);
}
