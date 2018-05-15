package com.app.chat.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.chat.models.Conversation;
import com.app.chat.models.User;
import com.app.chat.repositories.ConversationRepository;;

@Service
public class ConversationDetailsImpl implements ConversationDetails{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ConversationRepository ConversationRepository;
	
	@Override
	public List<Conversation> createConversation(List<User> list) {
		String uniqueID = UUID.randomUUID().toString();
		Conversation conversation = new Conversation();
		conversation.setActive(true);
		conversation.setConversationId(uniqueID);
		conversation.setUsers(list);
		conversation.setInitiatedBy(list.get(0).getUserId());
		conversation.setReciever(list.get(1).getUserId());
		String id = ConversationRepository.findByConversation(conversation.getInitiatedBy(), conversation.getReciever());
		logger.info("query value "+id);
		logger.info("outside conversation saving"+conversation.getInitiatedBy()+" "+conversation.getReciever());
		if(id == null) {
		logger.info("inside conversation saving"+conversation.getInitiatedBy()+" "+conversation.getReciever());
		conversation = ConversationRepository.save(conversation);
		List<Conversation> convers = new ArrayList<Conversation>();
		convers.add(conversation);
		return convers;
		}else {
			List<Conversation> convers = new ArrayList<Conversation>();
			convers = ConversationRepository.findByInitiatedBy(conversation.getInitiatedBy());
			return null;
		}
	}

	@Override
	public List<Conversation> getConversations(String user) {
		List<Conversation> list = ConversationRepository.findByUsersDisplayName(user);
		return list;
	}

	
}
