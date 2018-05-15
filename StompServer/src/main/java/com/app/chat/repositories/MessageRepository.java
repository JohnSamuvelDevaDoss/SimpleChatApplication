package com.app.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.chat.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
	
	public List<Message> findByConversationsConversationId(@Param("sender") String conversationId);
	
}
