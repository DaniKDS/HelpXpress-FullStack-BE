package com.supportportal.service.impl;

import com.supportportal.domain.Job;
import com.supportportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobServiceImpl{

    @Autowired
    private JobRepository jobRepository;

    public void addRandomJobs(int numberOfJobs) {
        List<Job> jobs = new ArrayList<>();
        Random random = new Random();

        // Array cu regiunile disponibile
        String[] regions = {"Muntenia", "Transilvania", "Banat", "Moldova", "Oltenia", "Dobrogea"};

        // Definirea corespondenței între regiuni și județe și orașe
        Map<String, List<Map.Entry<String, String>>> regionToCitiesAndCounties = new HashMap<>();
        regionToCitiesAndCounties.put("Muntenia", Arrays.asList(
            Map.entry("București", "București"),
            Map.entry("Ploiești", "Prahova"),
            Map.entry("Târgoviște", "Dâmbovița")
        ));
        regionToCitiesAndCounties.put("Transilvania", Arrays.asList(
            Map.entry("Cluj-Napoca", "Cluj"),
            Map.entry("Brașov", "Brașov"),
            Map.entry("Sibiu", "Sibiu")
        ));
        regionToCitiesAndCounties.put("Banat", Arrays.asList(
            Map.entry("Timișoara", "Timiș"),
            Map.entry("Reșița", "Caraș-Severin")
        ));
        regionToCitiesAndCounties.put("Moldova", Arrays.asList(
            Map.entry("Iași", "Iași"),
            Map.entry("Bacău", "Bacău")
        ));
        regionToCitiesAndCounties.put("Oltenia", Arrays.asList(
            Map.entry("Craiova", "Dolj"),
            Map.entry("Târgu Jiu", "Gorj")
        ));
        regionToCitiesAndCounties.put("Dobrogea", Arrays.asList(
            Map.entry("Constanța", "Constanța"),
            Map.entry("Tulcea", "Tulcea")
        ));


        String[] employmentTypes = {"full-time", "part-time", "contract", "temporar", "freelance"};

        for (int i = 0; i < numberOfJobs; i++) {
            String disabilityType = generateRandomDisabilityType(random);
            String selectedRegion = regions[random.nextInt(regions.length)];
            List<Map.Entry<String, String>> citiesAndCounties = regionToCitiesAndCounties.get(selectedRegion);
            int cityIndex = random.nextInt(citiesAndCounties.size());
            String selectedCity = citiesAndCounties.get(cityIndex).getKey();
            String selectedCounty = citiesAndCounties.get(cityIndex).getValue();

            Job job = Job.builder()
                .name(generateJobName(i, disabilityType))
                .description(generateJobDescription(disabilityType))
                .region(selectedRegion)
                .county(selectedCounty)
                .city(selectedCity)
                .accessibilityFeatures("Accesibil pentru scaun rulant, software de asistență, etc.")
                .isDedicatedForDisability(true)
                .disabilityType(disabilityType)
                .employmentType(employmentTypes[random.nextInt(employmentTypes.length)])
                .salaryRange(generateRandomSalary(random))
                .postingDate(new Date())
                .isRemote(random.nextBoolean())
                .employerId("EmployerID_" + random.nextInt(100))
                .employerName(generateEmployerName(random))
                .employerPhone(generateEmployerPhone(random))
                .status("activ")
                .build();

            jobs.add(job);
        }
        jobRepository.saveAll(jobs);
    }

    private String generateRandomDisabilityType(Random random) {
        String[] types = {"motorie", "vizuala", "auditiva", "neurologica"};
        return types[random.nextInt(types.length)];
    }

    private String generateRandomSalary(Random random) {
        int base = 1000 * (random.nextInt(5) + 2); // Salar între 2000 și 6000 RON
        int range = base + 1000; // +1000 RON
        return base + " - " + range + " RON";
    }

    private String generateJobName(int index, String disabilityType) {
        // Joburi special adaptate pentru persoane cu diferite tipuri de dizabilități
        String[] jobsForMotorDisabilities = {"Operator Calculator", "Programator", "Designer Grafic", "Analist Date"};
        String[] jobsForVisualDisabilities = {"Consultant Telefonic", "Specialist Resurse Umane", "Asistent Vocal", "Narrator Audiobook-uri"};
        String[] jobsForAuditoryDisabilities = {"Editor Text", "Administrator Rețea", "Asistent Administrativ", "Analizator Date"};
        String[] jobsForNeurologicalDisabilities = {"Specialist IT", "Tehnician Laborator", "Cercetător", "Developer Software"};

        // Selectarea array-ului corect pe baza tipului de dizabilitate
        String[] jobPrefixes;
        switch (disabilityType) {
            case "motorie":
                jobPrefixes = jobsForMotorDisabilities;
                break;
            case "vizuala":
                jobPrefixes = jobsForVisualDisabilities;
                break;
            case "auditiva":
                jobPrefixes = jobsForAuditoryDisabilities;
                break;
            case "neurologica":
                jobPrefixes = jobsForNeurologicalDisabilities;
                break;
            default:
                jobPrefixes = new String[] {"Specialist", "Consultant", "Asistent", "Coordonator"};
                break;
        }

        // Generarea numelui jobului bazat pe tipul de dizabilitate și index
        return jobPrefixes[index % jobPrefixes.length] + " pentru persoană cu dizabilitate: " + disabilityType;
    }

    private String generateJobDescription(String disabilityType) {
        String descriptionTemplate = "Oportunitate pentru persoana cu dizabilitate %s. Sprijinim integrarea profesională în domeniul %s.";
        String[] fields = {"IT", "educație", "servicii client", "administrație", "vânzări", "tehnic"};
        Random rand = new Random();
        return String.format(descriptionTemplate, disabilityType, fields[rand.nextInt(fields.length)]);
    }
    private String generateEmployerName(Random random) {
        String[] names = {"Tech Solutions", "Innovatech", "EduSmart", "HealthBridge", "QuickFinance", "SafeTravel", "GreenEnergy"};
        return names[random.nextInt(names.length)];
    }

    private String generateEmployerPhone(Random random) {
        // Generăm un număr de telefon valid în format românesc, presupunând numere de București pentru simplificare
        int num = 700000 + random.nextInt(99999);
        return "072" + num; // Prefix comun pentru telefoane mobile în România
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}