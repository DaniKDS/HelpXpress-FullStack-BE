package com.supportportal.service.impl;

import com.supportportal.domain.Organization;
import com.supportportal.domain.User;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private UserRepository userRepository;


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

    public void addRelationsBetweenUsersAndOrganizations() {
        List<User> users = userRepository.findAll();
        List<Organization> organizations = organizationRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            User user = users.get(random.nextInt(users.size()));
            Organization organization = organizations.get(random.nextInt(organizations.size()));

//            Set<Organization> userOrganizations = user.getOrganizations();
//            if (userOrganizations == null) {
//                userOrganizations = new HashSet<>();
//                user.setOrganizations(userOrganizations);
//            }
//            userOrganizations.add(organization);
//
//            Set<User> organizationUsers = organization.getUsers();
//            if (organizationUsers == null) {
//                organizationUsers = new HashSet<>();
//                organization.setUsers(organizationUsers);
//            }
//            organizationUsers.add(user);

            // Salvăm ambele entități pentru a persista relația în baza de date
            userRepository.save(user);
            organizationRepository.save(organization);
        }
    }
}