package com.chatop.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.Rental;
import com.chatop.service.RentalService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final RentalService rentalService;
	  
  	public RentalController(RentalService rentalService) {
      this.rentalService = rentalService;
  	}
  
	@GetMapping
	public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
	    return ResponseEntity.ok(rentalService.getAllRentals());
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<Rental> getOneRentalById(@PathVariable Integer id) {
	    Optional<Rental> optionalRental = rentalService.getOneRentalById(id);

	    if (optionalRental.isPresent()) {
	        Rental foundRental = optionalRental.get();
	        return ResponseEntity.ok(foundRental);
	    }
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<String> newRental(@RequestBody Rental newRental) {		
	    Rental createdRental = rentalService.createRental(newRental);

	    if (createdRental != null) {
	        return ResponseEntity.ok("Location créée avec succès !");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de la création de la location.");
	    
	}
	
	@PutMapping("/{id}")
	public void updateRentalById(Integer id) {
		
	}
}