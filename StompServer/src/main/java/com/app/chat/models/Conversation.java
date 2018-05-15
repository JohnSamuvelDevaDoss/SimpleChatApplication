package com.app.chat.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "conversation")
public class Conversation {
	
	@Id
	@Column(name="conversationId")
	private String conversationId;
	
	@Column(name="active")
	private boolean active;
	
	private Long initiatedBy;
	
	private Long reciever;
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,mappedBy = "conversations")
	private List<Message> messages = new ArrayList<>();
	
	
	@ManyToMany
	private List<User> users = new ArrayList<>();

	public Long getReciever() {
		return reciever;
	}

	public void setReciever(Long reciever) {
		this.reciever = reciever;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Long getInitiatedBy() {
		return initiatedBy;
	}

	public void setInitiatedBy(Long initiatedBy) {
		this.initiatedBy = initiatedBy;
	}
	

}
