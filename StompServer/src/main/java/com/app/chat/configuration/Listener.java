package com.app.chat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Async
    public void pushChangesToWebSocket() {
    	
          webSocket.convertAndSend("/app/send", "hello");
  }
}