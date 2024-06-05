package com.supportportal.service.impl;

import com.supportportal.domain.GazStation;
import com.supportportal.repository.BenzinarieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BenzinarieServiceImpl {

    @Autowired
    private BenzinarieRepository benzinarieRepository;

    public List<GazStation> getAllBenzinarii() {
        return benzinarieRepository.findAll();
    }

    public void generateRandomBenzinarii() {
        String[] brands = {"MOL", "PETROM", "ROMPETROL", "SOCAR", "OMW", "LUKOIL", "VEGAS", "GAZPROM"};
        Random random = new Random();
        List<GazStation> benzinarii = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            // Generarea numărului de telefon
            String telefon = "07" + (1000000 + random.nextInt(9000000)); // Generare număr de telefon random

            // Actualizarea generării adresei de email
            String email = "benzinarie" + (i + 1) + "@peco.ro"; // Generare email în formatul specificat

            GazStation benzinarie = GazStation.builder()
                .nume(brands[random.nextInt(brands.length)] + " Benzinăria " + (i + 1))
                .locatie("Locatie " + (i + 1))
                .brand(brands[random.nextInt(brands.length)])
                .telefon(telefon) // Setare telefon
                .email(email)    // Setare email
                .build();
            benzinarii.add(benzinarie);
        }
        benzinarieRepository.saveAll(benzinarii);
    }

    public GazStation findById(Long gazStationId) {
        return benzinarieRepository.findById(gazStationId).orElse(null);
    }
}
