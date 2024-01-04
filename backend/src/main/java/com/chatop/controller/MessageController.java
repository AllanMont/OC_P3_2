package com.chatop.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.Message;
import com.chatop.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	private final MessageService messageService;
	
  	public MessageController(MessageService messageService) {
        this.messageService = messageService;
    	}
  	
	@PostMapping
	  public ResponseEntity<Map<String, String>> newMessages(@RequestBody Message message) {
	    Message messageSaved = messageService.newMessages(message);
	    if (messageSaved != null) {
	      return ResponseEntity.ok(Map.of("message", "Message send with success"));
	    }
	      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    
	  }	
}
