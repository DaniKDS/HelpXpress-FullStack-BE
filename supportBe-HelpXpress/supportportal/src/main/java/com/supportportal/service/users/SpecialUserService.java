package com.supportportal.service.users;

import com.supportportal.domain.Assistant;
import com.supportportal.domain.SpecialUser;
import com.supportportal.domain.User;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.repository.users.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.supportportal.enumeration.Role.ROLE_ASSISTANT;

@Service
public class SpecialUserService {
    private final UserRepository userRepository;
    private final SpecialUserRepository specialUserRepository;
    private final AssistantRepository assistantRepository;

    public SpecialUserService(UserRepository userRepository, SpecialUserRepository specialUserRepository, AssistantRepository assistantRepository) {
        this.userRepository = userRepository;
        this.specialUserRepository = specialUserRepository;
        this.assistantRepository = assistantRepository;
    }

    public List<User> findAllSpecialPersons() {
        return userRepository.findAllByRole(ROLE_ASSISTANT.name());
    }

    public SpecialUser addNewSpecialUser(Long userId, String disease, String diseaseType, Long assistantId) {
        // Verificăm dacă există User-ul în baza de date
        User user = userRepository.findById(userId).orElseThrow(() ->
            new UsernameNotFoundException("User not found with ID: " + userId));

        // Verificăm dacă User-ul are deja un profil de SpecialUser
        if (specialUserRepository.existsByUserId(userId)) {
            throw new IllegalStateException("SpecialUser profile already exists for user ID: " + userId);
        }

        // Găsește asistentul asociat, dacă este necesar
        Assistant assistant = null;
        if (assistantId != null) {
            assistant = assistantRepository.findById(assistantId).orElseThrow(() ->
                new UsernameNotFoundException("Assistant not found with ID: " + assistantId));
        }

        // Creăm un nou SpecialUser
        SpecialUser specialUser = new SpecialUser();
        specialUser.setUser(user);
        specialUser.setDisease(disease);
        specialUser.setDiseaseType(diseaseType);
        specialUser.setAssistant(assistant);

        // Salvăm SpecialUser în baza de date
        specialUserRepository.save(specialUser);

        return specialUser;
    }
    public Optional<Object> findById(Long specialUserId) {
        return Optional.of(specialUserRepository.findById(specialUserId));
    }

    public List<User> findAllByRole(String name) {
        return userRepository.findAllByRole(name);
    }
    public List<SpecialUser> findAll() {
        return specialUserRepository.findAll();
    }
    private final Random random = new Random();
    public void saveManySpecialUsers() {
        // Listează toate tipurile de boli și tipurile acestora
        List<String> diseases = Arrays.asList("Diabet", "Hipertensiune", "Artrită", "Scleroză multiplă", "Parkinson");
        List<String> diseaseTypes = Arrays.asList("Motorie", "Vizuală", "Auditivă", "Neurologică");

        // Obține toți asistenții și userii cu rolul de USER
        List<User> usersWithRoleAssistant = userRepository.findAllByRole("ROLE_ASSISTANT");
        List<User> usersWithRoleUser = userRepository.findAllByRole("ROLE_USER");

        if (usersWithRoleAssistant.isEmpty()) {
            throw new IllegalStateException("There are no assistants to assign.");
        }

        // Populează SpecialUsers
        usersWithRoleUser.forEach(user -> {
            if (!specialUserRepository.existsByUserId(user.getId())) {
                SpecialUser specialUser = new SpecialUser();
                specialUser.setUser(user);
                specialUser.setDisease(diseases.get(random.nextInt(diseases.size())));
                specialUser.setDiseaseType(diseaseTypes.get(random.nextInt(diseaseTypes.size())));

                // Asignează un asistent la întâmplare și setează ID-ul acestuia
                User randomAssistantUser = usersWithRoleAssistant.get(random.nextInt(usersWithRoleAssistant.size()));
                Assistant randomAssistant = (Assistant) assistantRepository.findByUserId(randomAssistantUser.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Assistant not found with user ID: " + randomAssistantUser.getId()));
                specialUser.setAssistant(randomAssistant);

                // Salvăm SpecialUser în baza de date
                specialUserRepository.save(specialUser);
            }
        });
    }
    public List<SpecialUser> findAllSpecialUsers() {
        return specialUserRepository.findAll();
    }

    public SpecialUser findSpecialUserById(Long specialUserId) {
        return specialUserRepository.findById(specialUserId).orElseThrow(() ->
            new UsernameNotFoundException("SpecialUser not found with ID: " + specialUserId));
    }
}
