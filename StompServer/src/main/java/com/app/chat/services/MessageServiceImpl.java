package com.app.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.chat.models.Message;
import com.app.chat.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public Message saveMessages(Message message) {
		
		Message mess = messageRepository.save(message);
		return mess;
	}

	@Override
	public List<Message> getMessages(String conversationId) {
		
		List<Message>message = messageRepository.findByConversationsConversationId(conversationId);
		return message;
	}

	
}
