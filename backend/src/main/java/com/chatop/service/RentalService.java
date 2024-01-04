package com.chatop.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.model.Rental;
import com.chatop.repository.RentalRepository;

@Service
public class RentalService {

  @Autowired
  private RentalRepository rentalRepository;
  
  public Map<String, List<Rental>> getAllRentals() {
	    return Collections.singletonMap("rentals",rentalRepository.findAll());
	}

  public Optional<Rental> getOneRentalById(final Integer id) {
      return rentalRepository.findById(id);
  }

  public Rental createRental(Rental Rental) {
      Rental savedRental = rentalRepository.save(Rental);
      return savedRental;
  }
}