package com.app.chat.services;

import java.util.List;

import com.app.chat.models.Message;

public interface MessageService {

	public Message saveMessages(Message message);
	public List<Message> getMessages(String conversationId);
}
