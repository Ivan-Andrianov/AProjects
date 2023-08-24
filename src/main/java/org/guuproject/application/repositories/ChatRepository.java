package org.guuproject.application.repositories;

import org.guuproject.application.models.Chat;
import org.guuproject.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    Chat findChatByUser1AndUser2(User user1, User user2);
    Chat findChatById(Long id);
}
