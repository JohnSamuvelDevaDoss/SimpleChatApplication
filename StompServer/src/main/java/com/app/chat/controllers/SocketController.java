package com.app.chat.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.chat.models.Conversation;
import com.app.chat.models.Message;
import com.app.chat.models.Message2;
import com.app.chat.models.User;
import com.app.chat.services.ConversationDetails;
import com.app.chat.services.MessageService;
import com.app.chat.services.UserDetails;

/***
 * 
 * @author johnsamuveld
 *
 */
@RestController
public class SocketController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final SimpMessagingTemplate template;

    @Autowired
    SocketController(SimpMessagingTemplate template){
        this.template = template;
    }
    
    @Autowired
    private UserDetails userDetails;
    
    @Autowired
    private ConversationDetails conversationDetails;
    
    @Autowired
    private MessageService messageService;
    
    @CrossOrigin
    @Transactional
    @RequestMapping(value = "/getusers",method=RequestMethod.GET,produces="application/json")
    public List<User> getUserList(){
    	
    	logger.info("In Getting Users");
    	logger.info("getting users");
    	List<User> userList = userDetails.getUserList();
    	logger.info("Users Recieved "+ userList);
    	
    	return userList;
    }
    
    @CrossOrigin
    @RequestMapping(value = "/createsession",method=RequestMethod.POST,produces="application/json",consumes="application/json")
    public String createSession(@RequestBody Map<String, String> json) {
    	logger.info("In Getting Users"+json.get("user"));
    	String uniqueID = UUID.randomUUID().toString();
    	userDetails.saveSession(uniqueID,json.get("user"));
    	return uniqueID;
    }
    
    
    @CrossOrigin
    @RequestMapping(value = "/createconversations",method=RequestMethod.POST,produces="application/json", consumes="application/json")
    public List<Conversation> createConversation(@RequestBody List<User> list) {
    	
    	String session = "";
    	logger.info("In Create Conversation"+list);
    	List<Conversation> convers = new ArrayList<Conversation>();
    	/*for(int i=0;i<list.length;i++) {
    		users.add(list[i]);
    	}*/
    	convers = conversationDetails.createConversation(list);
    	for(User user:list) {
    		logger.info("in sending"+ user.getSessionId());
    		session = userDetails.getSession(user.getDisplayName());
    		logger.info(session);
    		this.template.convertAndSend("/message/id/"+session+"/conversations", convers);	
    	}
    	return convers;
    }
    
    @CrossOrigin
    @RequestMapping(value = "/getconversations",method=RequestMethod.POST,produces="application/json", consumes="application/json")
    public List<Conversation> getConversations(@RequestBody Map<String, String> userid) {
    	
    	logger.info("In Get Conversations"+userid.get("user"));
    	List<Conversation> list = conversationDetails.getConversations(userid.get("user"));
    	
    	return list;
    }
    
    

    @RequestMapping(value = "/send",method=RequestMethod.POST,produces="application/json", consumes="application/json")
    @CrossOrigin
    public Message onReceivedMesage(@RequestBody Message2 json){
    	String session="";
    	logger.info("In send Message"+json);
    	Message message = new Message();
    	Conversation conversation = new Conversation();
    	conversation.setConversationId(json.getConversations());
    	message.setAuthor(json.getAuthor());
    	message.setConversations(conversation);
    	message.setSendTo(json.getSendTo());
    	message.setText(json.getText());
    	Message messageResponse = messageService.saveMessages(message);
    	if(messageResponse.getConversations().getConversationId() != null && messageResponse.getConversations().getConversationId() != "") {
    	session = userDetails.getSession(json.getAuthor());
        this.template.convertAndSend("/message/id/"+session, json);
        session = userDetails.getSession(json.getSendTo());
        this.template.convertAndSend("/message/id/"+session, json);
    	 }
        return messageResponse;
    }
}
