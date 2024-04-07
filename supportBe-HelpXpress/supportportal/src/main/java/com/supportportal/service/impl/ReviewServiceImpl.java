package com.supportportal.service.impl;

import com.supportportal.domain.Review;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.ReviewRepository;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.repository.users.UserRepository;
import com.supportportal.service.inter.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SpecialUserRepository specialUserRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    private static final String[] COMMENTS = {
        "Accesibilitatea aplicației este excelentă.",
        "Am întâmpinat dificultăți în navigarea prin meniu.",
        "Asistentul personal a fost de mare ajutor.",
        "Instrucțiunile oferite nu au fost suficient de clare pentru nevoile mele speciale.",
        "Interfața prietenoasă cu utilizatorul face utilizarea aplicației o plăcere.",
        "Aș aprecia mai multe opțiuni de personalizare pentru nevoile mele unice.",
        "Funcția de asistență vocală îmbunătățește semnificativ experiența de utilizare.",
        "Programările cu medicul sunt ușor de gestionat prin aplicație.",
        "Am întâmpinat unele probleme tehnice în timpul utilizării.",
        "Serviciul de suport pentru clienți este prompt și eficient.",
        "Apreciez accesul la diverse resurse și informații despre îngrijirea sănătății.",
        "Secțiunea de sfaturi practice este foarte utilă și bine structurată.",
        "Videoclipurile educative oferite sunt ușor de înțeles și relevante pentru condiția mea.",
        "Feedback-ul rapid de la medici prin intermediul aplicației este extrem de valoros.",
        "Funcționalitatea de a urmări progresul personal este motivantă și informativă.",
        "Apreciez diversitatea comunității și posibilitatea de a interacționa cu alți utilizatori.",
        "Opțiunile de filtrare pentru căutarea informațiilor sunt un pic limitate.",
        "Am fost încântat(ă) să găsesc atât de multe resurse accesibile într-un singur loc.",
        "Sugestiile personalizate pe baza condiției mele medicale sunt impresionante.",
        "Aș dori mai multe opțiuni de interacțiune directă cu specialiștii.",
        "Funcția de alertă pentru medicamente este incredibil de utilă.",
        "Secțiunea de întrebări frecvente răspunde la multe dintre nelămuririle mele.",
        "Instrucțiunile pentru exercițiile fizice adaptate sunt ușor de urmărit.",
        "Accesul la consultanță profesională prin intermediul aplicației este un mare avantaj.",
        "Funcționalitățile pentru gestionarea stresului și anxietății sunt bine gândite.",
    };
    private Random random = new Random();
    public void addRandomReviews() {
        // Presupunem că există metode care îți returnează o listă cu id-urile valide pentru fiecare entitate
        List<Long> specialUserIds = getValidSpecialUserIds();
        List<Long> organizationIds = getValidOrganizationIds();
        List<Long> doctorIds = getValidDoctorIds();
        List<Long> assistantIds = getValidAssistantIds();

        // Creăm un număr specificat de recenzii
        for (int i = 0; i < 100; i++) {
            Review review = new Review();

            // Alege id-uri aleatorii pentru fiecare entitate implicată în recenzie
            Long specialUserId = pickRandomId(specialUserIds);
            Long organizationId = pickRandomId(organizationIds);
            Long doctorId = pickRandomId(doctorIds);
            Long assistantId = pickRandomId(assistantIds);

            // Validate and set the foreign keys if the IDs are valid
            if (specialUserId != null && specialUserRepository.existsById(specialUserId)) {
                review.setSpecialUserId(specialUserId);
            }
            if (organizationId != null && organizationRepository.existsById(organizationId)) {
                review.setOrganizationId(organizationId);
            } else {
                continue; // Skip to the next iteration if the organization ID is not valid
            }
            if (doctorId != null && doctorRepository.existsById(doctorId)) {
                review.setDoctorId(doctorId);
            }
            if (assistantId != null && assistantRepository.existsById(assistantId)) {
                review.setAssistantId(assistantId);
            }

            // Alege un comentariu aleatoriu
            String comment = COMMENTS[random.nextInt(COMMENTS.length)];

            // Alege un rating aleatoriu între 1 și 5
            int rating = random.nextInt(5) + 1;

            // Setează data recenziei la o dată aleatoare recentă
            LocalDate reviewLocalDate = LocalDate.now().minusDays(random.nextInt(365));
            Date reviewDate = Date.from(reviewLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Setăm datele alese pentru recenzie
            review.setComment(comment);
            review.setRating(rating);
            review.setReviewDate(reviewDate);

            // Save the review if all foreign keys are valid
            reviewRepository.save(review);
        }
    }
    public void createReviewWithValidOrganization() {
        // Retrieve all valid organization IDs
        List<Long> validOrganizationIds = organizationRepository.findAllIds();

        // Ensure that the list is not empty
        if (validOrganizationIds.isEmpty()) {
            throw new IllegalStateException("No organizations available to review.");
        }

        // Logic to select a valid organization ID - for example, picking one at random
        Long validOrganizationId = 235L; // pickRandomId(validOrganizationIds);

        // Create a new Review instance
        Review review = new Review();

        // Set the organization ID to a valid ID
        review.setOrganizationId(validOrganizationId);

        // Set other properties for the review
        review.setComment("Great organization with excellent services.");
        review.setRating(5); // Random rating between 1 and 5
        review.setReviewDate(new Date()); // Current date for the review

        // Set foreign keys to null or to valid existing IDs
        review.setDoctorId(181L); // Assuming this can be null if not reviewing a doctor
        review.setSpecialUserId(180L); // Assuming this can be null if not linked to a special user
        review.setAssistantId(131L); // Assuming this can be null if not linked to an assistant

        // Save the review
        reviewRepository.save(review);
    }


    private Long pickRandomId(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        return ids.get(random.nextInt(ids.size()));
    }

    // Trebuie să implementezi metodele de a obține id-urile valide pentru fiecare entitate
    private List<Long> getValidSpecialUserIds() {
        return specialUserRepository.findAllIds();
    }



    private List<Long> getValidOrganizationIds() {
        return organizationRepository.findAllIds();
    }

    private List<Long> getValidDoctorIds() {
        return doctorRepository.findAllIds();
    }

    private List<Long> getValidAssistantIds() {
        return assistantRepository.findAllIds();
    }

}
