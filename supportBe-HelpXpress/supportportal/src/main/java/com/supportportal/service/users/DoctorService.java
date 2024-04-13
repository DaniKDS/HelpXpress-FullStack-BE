package com.supportportal.service.users;

import com.supportportal.domain.Doctor;
import com.supportportal.domain.SpecialUser;
import com.supportportal.domain.User;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.supportportal.enumeration.Role.ROLE_DOCTOR;
import static com.supportportal.enumeration.Role.ROLE_USER;

@Service
public class DoctorService {
    private final UserRepository userRepository;
    @Autowired
    private SpecialUserService specialUserService;
    private final SpecialUserRepository specialUserRepository;
    private final DoctorRepository doctorRepository;

    public DoctorService(UserRepository userRepository, SpecialUserRepository specialUserRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.specialUserRepository = specialUserRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    private Random random = new Random();

    public void saveManyDoctors() {
        List<User> doctors = userRepository.findAllByRole(ROLE_DOCTOR.name());
        List<SpecialUser> specialUsers = specialUserService.findAll();

        // Assuming that the SpecialUser entity is associated with a User entity with ROLE_USER
        List<SpecialUser> assignableSpecialUsers = specialUsers.stream()
            .filter(specialUser -> specialUser.getUser().getRole().equals(ROLE_USER.name()))
            .collect(Collectors.toList());

        // We won't throw an exception if this list is empty, we'll just have some Doctors without SpecialUsers
        doctors.forEach(doctorUser -> {
            if (!doctorRepository.existsByUserId(doctorUser.getId())) {
                Doctor doctor = new Doctor();
                doctor.setUser(doctorUser); // This will set the user_id to the Doctor's User
                doctor.setExperienceYears(random.nextInt(20) + 1);

                // Randomly assign the speciality in Romanian as per your requirement
                String[] specialities = {"Neurologică", "Auditivă", "Motorie", "Vizuală"};
                doctor.setSpeciality(specialities[random.nextInt(specialities.length)]);

                // If there are assignable SpecialUsers left, assign one to the Doctor
                if (!assignableSpecialUsers.isEmpty()) {
                    SpecialUser specialUser = assignableSpecialUsers.remove(random.nextInt(assignableSpecialUsers.size()));
                    doctor.setSpecialUser(specialUser); // Set the randomly picked SpecialUser to the Doctor
                } // Otherwise, do not set a SpecialUser

                doctorRepository.save(doctor);
            }
        });
    }
    public SpecialUser findSpecialUserByDoctorUsername(String username) {
        Doctor doctor = doctorRepository.findByUserUsername(username);
        if (doctor != null) {
            return doctor.getSpecialUser();
        } else {
            throw new UsernameNotFoundException("Doctor with username " + username + " not found");
        }
    }
}