package com.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
}
