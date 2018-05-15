package com.app.chat.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.chat.models.User;
import com.app.chat.repositories.UsersRepository;

/***
 * 
 * @author johnsamuveld
 *
 */

@Service
@Transactional
public class UserServiceImpl implements UserDetails {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Override
	public List<User> getUserList() {
		List<User> userList = usersRepository.findAll();
		return userList;
	}

	@Override
	public void saveSession(String session,String user) {
		
		usersRepository.setSessionIdForUser(session,user);
		
	}

	@Override
	public String getSession(String user_id) {
		User users  = usersRepository.findByDisplayName(user_id);
		
		return users.getSessionId();
	}
	
}
