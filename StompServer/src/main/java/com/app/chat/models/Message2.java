package com.app.chat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Message2 {
	
	@JsonIgnore
	private Long message_id;
	
	
	private String author;
	
	
	private String sendTo;
	
	private String conversations;
	
	
	private String text;
	

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public String getConversations() {
		return conversations;
	}

	public void setConversations(String conversations) {
		this.conversations = conversations;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	@Override
	public String toString() {
		return "Message [author=" + author + ", sendTo=" + sendTo + ", text=" + text + "]";
	}
	
}
