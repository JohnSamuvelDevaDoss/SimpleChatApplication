package com.app.chat.services;

import java.util.List;
import com.app.chat.models.Conversation;
import com.app.chat.models.User;

public interface ConversationDetails {
	
	public List<Conversation> createConversation(List<User> list);
	
	public List<Conversation> getConversations(String user);
}
