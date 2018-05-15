package com.app.chat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/***
 * 
 * @author johnsamuveld
 *
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer  {

	
	
	    @Override
	    @CrossOrigin
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();
	                
	    }

	    @Override
	    @CrossOrigin
	    public void configureMessageBroker(MessageBrokerRegistry registry) {
	        registry.enableSimpleBroker("/message");
	    }
}
