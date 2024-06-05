package com.supportportal.controller;

import com.supportportal.domain.GazStation;
import com.supportportal.service.EmailService;
import com.supportportal.service.impl.BenzinarieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/benzinarii")
public class BenzinarieController {

    @Autowired
    private BenzinarieServiceImpl benzinarieService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<GazStation>> getAllBenzinarii() {
        List<GazStation> benzinarii = benzinarieService.getAllBenzinarii();
        return ResponseEntity.ok(benzinarii);
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateBenzinarii() {
        benzinarieService.generateRandomBenzinarii();
        return ResponseEntity.ok("Benzinarii generate cu succes.");
    }

    @PostMapping("/sendAssistanceRequest")
    public ResponseEntity<String> sendAssistanceRequest(@RequestBody Map<String, Object> requestDetails) {
        try {
            String email = "cordisdany@gmail.com";
            requestDetails.put("email", email);

            emailService.createAssistanceRequestEmail(requestDetails);

            return ResponseEntity.ok("Cererea a fost trimisă cu succes.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("A apărut o eroare la trimiterea cererii: " + e.getMessage());
        }
    }
}
