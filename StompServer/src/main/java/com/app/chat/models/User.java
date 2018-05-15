package com.app.chat.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * 
 * @author johnsamuveld
 *
 */
@Entity
@Table(name="user_details")
public class User {
	
	@Id @Column(name="userId")
	public Long userId;
	
	@Column(name="first_name")
	public String first_name;
	
	@Column(name="last_name")
	public String last_name;
	
	@Column(name="displayName")
	public String displayName;
	
	@Column(name="avatar_url")
	public String avatar_url;
	
	@JsonIgnore
	@ManyToMany(mappedBy="users")
	public List<Conversation> conversations= new ArrayList<>();
	
	@Column(name="password")
	private String password;
	
	@Column(name="sessionId")
	private String sessionId;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public List<Conversation> getConversations() {
		return conversations;
	}
	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}
	
	
	@Override
	public String toString() {
		return "User [ userId=" + userId + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", displayName=" + displayName + ", avatar_url=" + avatar_url + "]";
	}
}
