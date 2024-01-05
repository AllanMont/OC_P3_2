package com.chatop.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
      // Save Rental with increment id
      int newId = rentalRepository.findAll().size() + 1;
      Rental.setOwner_id(newId);

      Rental savedRental = rentalRepository.save(Rental);
      return savedRental;
  }

 public String uploadPicture(MultipartFile file) {
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    Path uploadPath = Paths.get("uploads");
    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectories(uploadPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try (InputStream inputStream = file.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return uploadPath + "/" + fileName;
  }

}