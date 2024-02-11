package com.chatop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    
}
