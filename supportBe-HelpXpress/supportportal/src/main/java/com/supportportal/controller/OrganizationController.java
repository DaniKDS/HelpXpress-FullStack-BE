package com.supportportal.controller;

import com.supportportal.service.impl.OrganizationServiceImpl;
import com.supportportal.service.inter.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationServiceImpl organizationService;

    @Autowired
    public OrganizationController(OrganizationServiceImpl organizationService1) {

        this.organizationService = organizationService1;
    }

    @PostMapping("/addManyOrganizations")
    public ResponseEntity<String> addManyOrganizations() {
        try {
            organizationService.addManyOrganizations();
            return ResponseEntity.ok("S-au adăugat cu succes 100 de organizații.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A apărut o eroare la adăugarea organizațiilor: " + e.getMessage());
        }
    }

    @PostMapping("/addManySpecialUserOrganizations")
    public ResponseEntity<String> addManySpecialUserOrganizations() {
        try {
            organizationService.addManySpecialUserOrganizations();
            return ResponseEntity.ok("S-au adăugat cu succes 100 de asocieri între SpecialUser și Organization.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("A apărut o eroare la adăugarea asocierilor: " + e.getMessage());
        }
    }
}
