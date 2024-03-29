package com.chatop.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.RentalDto;
import com.chatop.model.Rental;
import com.chatop.service.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

  private final RentalService rentalService;
	  
  	public RentalController(RentalService rentalService) {
      this.rentalService = rentalService;
  	}
  
	@GetMapping
	@Operation(summary = "Obtenir toutes les locations", description = "Récupère la liste de toutes les locations.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Locations trouvées"),
			@ApiResponse(responseCode = "404", description = "Locations non trouvées")
	})
	public ResponseEntity<Map<String, List<Rental>>> getAllRentals() {
	    return ResponseEntity.ok(rentalService.getAllRentals());
	}

	
	@GetMapping("/{id}")
	@Operation(summary = "Obtenir une location par ID", description = "Récupère une location en fonction de son ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Location trouvée"),
			@ApiResponse(responseCode = "404", description = "Location non trouvée")
	})
	public ResponseEntity<Rental> getOneRentalById(@PathVariable Integer id) {
	    Optional<Rental> optionalRental = rentalService.getOneRentalById(id);

	    if (optionalRental.isPresent()) {
	        Rental foundRental = optionalRental.get();
	        return ResponseEntity.ok(foundRental);
	    }
		return ResponseEntity.notFound().build();
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Créer une location", description = "Créer une location.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Location créée avec succès"),
			@ApiResponse(responseCode = "400", description = "Échec de la création de la location")
	})
	public ResponseEntity<String> newRental( HttpServletRequest request,
      @ModelAttribute("rentals") RentalDto rentalsDto) {

		String urlPicture = rentalService.uploadPicture(rentalsDto.getPicture());
		if(urlPicture == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de l'upload de l'image."); }		
	    
		Rental newRental = new Rental();
		newRental.setName(rentalsDto.getName());
		newRental.setPrice(rentalsDto.getPrice());
		newRental.setSurface(rentalsDto.getSurface());
		newRental.setPicture(urlPicture);
		newRental.setDescription(rentalsDto.getDescription());
		Rental createdRental = rentalService.createRental(newRental);

	    if (createdRental != null) return ResponseEntity.ok("Location créée avec succès !");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de la création de la location.");
	    
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Mettre à jour une location", description = "Mettre à jour une location.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Location mise à jour avec succès"),
			@ApiResponse(responseCode = "400", description = "Échec de la mise à jour de la location"),
			@ApiResponse(responseCode = "404", description = "Location non trouvée")
	})
	public ResponseEntity<String> updateRental(@PathVariable Integer id, @ModelAttribute("rentals") RentalDto rentalsDto) {
		Optional<Rental> optionalRental = rentalService.getOneRentalById(id);
		if (optionalRental.isPresent()) {
			Rental foundRental = optionalRental.get();
			foundRental.setName(rentalsDto.getName());
			foundRental.setPrice(rentalsDto.getPrice());
			foundRental.setSurface(rentalsDto.getSurface());
			foundRental.setDescription(rentalsDto.getDescription());
			Rental updatedRental = rentalService.updateRental(foundRental);
			if (updatedRental != null) {
				return ResponseEntity.ok("Location mise à jour avec succès !");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Échec de la mise à jour de la location.");
		}
		return ResponseEntity.notFound().build();
	}
}