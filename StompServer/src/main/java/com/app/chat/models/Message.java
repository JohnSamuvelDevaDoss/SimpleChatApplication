package com.app.chat.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="message")
@SuppressWarnings("deprecation")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Message {
	
	@JsonIgnore
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="message_id")
	private Long message_id;
	
	@Column(name="author")
	private String author;
	
	@Column(name="sendTo")
	private String sendTo;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversationId", nullable = false)
	private Conversation conversations;
	
	@Column(name="text")
	private String text;
	

	public Long getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Long message_id) {
		this.message_id = message_id;
	}

	public Conversation getConversations() {
		return conversations;
	}

	public void setConversations(Conversation conversations) {
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
