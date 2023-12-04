package org.guuproject.application.repositories;

import org.guuproject.application.models.Chat;
import org.guuproject.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    Chat findChatById(Long id);
}
