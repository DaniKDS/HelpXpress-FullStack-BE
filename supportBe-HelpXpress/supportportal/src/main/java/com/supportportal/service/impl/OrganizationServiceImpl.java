package com.supportportal.service.impl;

import com.supportportal.domain.Organization;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public void addBulkOrganizations() {
        List<Organization> organizations = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            Organization organization = Organization.builder()
                .name("Organizația " + i)
                .type("Tipul " + (i % 5 == 0 ? "Spital" : "Centru de recuperare")) // Alternare simplă pentru exemplificare
                .address("Adresa " + i + ", Oraș, România")
                .description("Descriere pentru Organizația " + i + ". O descriere mai detaliată aici.")
                .phone("07xx-xxxxxx" + i)
                .build();

            organizations.add(organization);
        }

        organizationRepository.saveAll(organizations);
    }
}