package com.supportportal.controller;

import com.supportportal.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review") // Acesta este path-ul de bază pentru toate endpoint-urile din acest controller
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping("/addRandomReviews")
    public ResponseEntity<String> addRandomReviews() {
        try {
            reviewService.addRandomReviews();
            return ResponseEntity.ok("Recenzii adăugate cu succes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Eroare la adăugarea recenziilor: " + e.getMessage());
        }
    }

}
