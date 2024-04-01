package com.supportportal.controller;

import com.supportportal.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review") // Acesta este path-ul de bază pentru toate endpoint-urile din acest controller
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/addBulkReviews")
    public ResponseEntity<String> addBulkReviews() {
        try {
            reviewService.addBulkReviews();
            return ResponseEntity.ok("100 de recenzii au fost adăugate cu succes.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A apărut o eroare la adăugarea recenziilor.");
        }
    }
}
