package com.supportportal.controller;

import com.supportportal.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/addBulkOrganizations")
    public ResponseEntity<String> addBulkOrganizations() {
        organizationService.addBulkOrganizations();
        return ResponseEntity.ok("50 de organizații au fost adăugate cu succes.");
    }

    @PostMapping("/addUserOrganizationRelations")
    public ResponseEntity<String> addUserOrganizationRelations() {
        organizationService.addRelationsBetweenUsersAndOrganizations();
        return ResponseEntity.ok("200 de relații utilizator-organizație au fost adăugate cu succes.");
    }
}
