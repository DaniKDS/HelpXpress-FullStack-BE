package com.supportportal.service.impl;

import com.supportportal.domain.Assistant;
import com.supportportal.domain.Organization;
import com.supportportal.domain.SpecialUser;
import com.supportportal.domain.User;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.repository.users.UserRepository;
import com.supportportal.service.inter.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    private SpecialUserRepository specialUserRepository;

    private Random random = new Random();

    // Liste cu date pentru generarea aleatoare
    private List<String> organizationNames = List.of("Spitalul General", "Centrul de Recuperare Vitalitate",
        "Clinica Speranța", "Institutul de Neurologie", "Centrul de Terapii Inovative", "Spitalul de Copii Arcadia");

    private List<String> organizationTypes = List.of("Spital", "Centru de Recuperare", "Centru de Tratament",
        "Clinică de Terapii", "Institut pentru Drepturile Persoanelor cu Dizabilități");

    private List<String> organizationDescriptions = List.of("Servicii medicale de top pentru toată familia.",
        "Recuperare și tratamente personalizate pentru afecțiuni neurologice.",
        "Centru lider în terapia vizuală și auditivă.",
        "Dedicat susținerii drepturilor persoanelor cu diverse dizabilități.");

    private List<String> organizationAddresses = List.of("Strada Sănătății 15", "Bulevardul Libertății 45",
        "Aleea Florilor 9", "Strada Mare 138", "Drumul Taberei 22");

    public OrganizationServiceImpl(SpecialUserRepository specialUserRepository) {
        this.specialUserRepository = specialUserRepository;
    }

    // Metoda pentru a adăuga 100 de organizații aleatorii
    public void addManyOrganizations() {
        List<Organization> organizations = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String name = organizationNames.get(random.nextInt(organizationNames.size()));
            String type = organizationTypes.get(random.nextInt(organizationTypes.size()));
            String description = organizationDescriptions.get(random.nextInt(organizationDescriptions.size()));
            String address = organizationAddresses.get(random.nextInt(organizationAddresses.size()));
            String phone = "07" + (random.nextInt(9000) + 1000); // Generăm un număr de telefon aleatoriu

            Organization organization = new Organization();
            organization.setName(name);
            organization.setType(type);
            organization.setDescription(description);
            organization.setAddress(address);
            organization.setPhone(phone);

            organizations.add(organization);
        }

        organizationRepository.saveAll(organizations); // Salvăm toate organizațiile generate în baza de date
    }
    public void addManySpecialUserOrganizations() {
        // Obținem toate id-urile SpecialUser și Organization
        List<Long> specialUserIds = specialUserRepository.findAll().stream()
            .map(SpecialUser::getId)
            .collect(Collectors.toList());
        List<Long> organizationIds = organizationRepository.findAll().stream()
            .map(Organization::getId)
            .collect(Collectors.toList());

        if (specialUserIds.isEmpty() || organizationIds.isEmpty()) {
            throw new IllegalStateException("SpecialUser sau Organization nu au înregistrări pentru a crea relații.");
        }

        // Generăm și salvăm 100 de perechi aleatoare
        for (int i = 0; i < 100; i++) {
            // Selectăm aleator un SpecialUser și o Organization
            Long specialUserId = specialUserIds.get(random.nextInt(specialUserIds.size()));
            Long organizationId = organizationIds.get(random.nextInt(organizationIds.size()));

            // Găsim entitățile pe baza id-urilor selectate
            SpecialUser specialUser = specialUserRepository.findById(specialUserId)
                .orElseThrow(() -> new IllegalStateException("SpecialUser cu id-ul " + specialUserId + " nu a fost găsit."));
            Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalStateException("Organization cu id-ul " + organizationId + " nu a fost găsit."));

            // Adăugăm organizația la specialUser și salvăm specialUser
            specialUser.getOrganization().add(organization);
            specialUserRepository.save(specialUser);
        }
    }
    public List<Organization> findAllOrganization() {
        return organizationRepository.findAll();
    }

    public Organization findOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

}