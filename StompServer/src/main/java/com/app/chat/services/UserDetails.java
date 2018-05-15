package com.app.chat.services;

import java.util.List;
import com.app.chat.models.User;

/***
 * 
 * @author johnsamuveld
 *
 */
public interface UserDetails {
	
	public List<User> getUserList();
	public void saveSession(String session,String user);
	public String getSession(String user_id);

}
