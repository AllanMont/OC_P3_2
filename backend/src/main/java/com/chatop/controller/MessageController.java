package com.chatop.controller;

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
	  public ResponseEntity<String> newMessages(@RequestBody Message message) {
		Message newMessage = messageService.newMessages(message);
		if(newMessage == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); }
		return ResponseEntity.ok("Message envoyé");
	}
}
