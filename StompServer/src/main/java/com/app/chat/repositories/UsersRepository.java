package com.app.chat.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.chat.models.User;

/***
 * 
 * @author johnsamuveld
 *
 */
@Repository
public interface UsersRepository extends JpaRepository<User, String> {

	@Transactional
	@Modifying
	@Query("UPDATE User SET sessionId= :session where display_name=:user")
	public void setSessionIdForUser(@Param("session") String session,@Param("user") String user);
	
	public User findByDisplayName(@Param("user_id")String user_id);

}
