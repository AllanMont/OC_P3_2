package com.chatop.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "message")
public class Message {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Integer id;
	  private Integer rental_id;
	  private Integer user_id;
	  private String message;
	  private LocalDateTime created_at;
	  private LocalDateTime updated_at;

}
