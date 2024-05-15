package com.supportportal.controller;

import com.supportportal.domain.GazStation;
import com.supportportal.service.impl.BenzinarieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benzinarii")
public class BenzinarieController {

    @Autowired
    private BenzinarieServiceImpl benzinarieService;

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
}
