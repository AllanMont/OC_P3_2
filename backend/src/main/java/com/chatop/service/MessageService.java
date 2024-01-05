package com.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.model.Message;
import com.chatop.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {

      @Autowired
	  private MessageRepository messageRepository;

	  public Message newMessages(Message message) {
		int newId = messageRepository.findAll().size() + 1;
		message.setId(newId);
		message.setCreated_at(java.time.LocalDateTime.now());
		message.setUpdated_at(java.time.LocalDateTime.now());
		Message savedMessage = messageRepository.save(message);
		return savedMessage;
	  }
}
