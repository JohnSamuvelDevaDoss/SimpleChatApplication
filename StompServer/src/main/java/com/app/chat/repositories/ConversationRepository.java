package com.app.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.chat.models.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

	@Query(nativeQuery=true,value="select conversation_id from conversation c where "
			+ "c.active=true and (c.reciever=:reciever or c.reciever=:sender) and (c.initiated_by=:sender or c.initiated_by=:reciever)")
	public String findByConversation(@Param("sender") Long sender,@Param("reciever") Long reciever);
	
	public List<Conversation> findByInitiatedBy(@Param("sender") Long sender);
	
	public List<Conversation> findByUsersDisplayName(@Param("sender") String sender);
}
