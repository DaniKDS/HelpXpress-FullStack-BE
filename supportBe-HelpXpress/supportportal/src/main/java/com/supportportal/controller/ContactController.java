package com.supportportal.controller;

import com.supportportal.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendContactEmail")
    public ResponseEntity<Map<String, String>> sendContactEmail(@RequestBody Map<String, Object> contactDetails) {
        try {
            emailService.createContactEmail(contactDetails);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Mesajul a fost trimis cu succes.");
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "A apărut o eroare la trimiterea mesajului: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
    @PostMapping("/sendReviewEmail")
    public ResponseEntity<Map<String, String>> sendReviewEmail(@RequestBody Map<String, Object> reviewDetails) {
        try {
            emailService.createReviewEmail(reviewDetails);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Recenzia a fost trimisă cu succes.");
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "A apărut o eroare la trimiterea recenziei: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
