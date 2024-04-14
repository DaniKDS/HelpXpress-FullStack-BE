package com.supportportal.service.users;

import com.supportportal.domain.Assistant;
import com.supportportal.domain.User;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AssistantService {

    private final UserRepository userRepository;
    private final AssistantRepository assistantRepository;

    @Autowired
    public AssistantService(UserRepository userRepository, AssistantRepository assistantRepository) {
        this.userRepository = userRepository;
        this.assistantRepository = assistantRepository;
    }

    public List<Assistant> findAllAssistants() {
        return assistantRepository.findAll();
    }

    private Random random = new Random();
    public void saveManyAssistants() {
        List<User> usersWithRoleAssistant = userRepository.findAllByRole("ROLE_ASSISTANT");
        List<String> specialitati = Arrays.asList("Cardiologie", "Dermatologie", "Neurologie", "Pediatrie", "Chirurgie");
        List<String> grade = Arrays.asList("Asistent", "Senior", "Expert", "Supervizor");

        for (User user : usersWithRoleAssistant) {
            if (!assistantRepository.existsByUserId(user.getId())) {
                Assistant assistant = new Assistant();
                assistant.setUser(user);
                // Generăm un număr aleatoriu de ani de experiență între 1 și 20
                assistant.setExperienceYears(random.nextInt(20) + 1);
                // Selectăm o specialitate la întâmplare din lista definită
                assistant.setSpeciality(specialitati.get(random.nextInt(specialitati.size())));
                // Selectăm un grad la întâmplare din lista definită
                assistant.setGrade(grade.get(random.nextInt(grade.size())));
                assistantRepository.save(assistant);
            }
        }
    }

    public Assistant findAssistantById(Long assistantId) {
        return assistantRepository.findById(assistantId).orElse(null);
    }

    public Assistant findAssistantByUserId(Long id) {
        return (Assistant) assistantRepository.findByUserId(id).orElse(null);
    }
}
