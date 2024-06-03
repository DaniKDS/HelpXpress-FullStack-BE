package com.supportportal.service.users;

import com.supportportal.domain.*;
import com.supportportal.repository.users.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.supportportal.enumeration.Role.ROLE_ASSISTANT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SpecialUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SpecialUserRepository specialUserRepository;

    @Mock
    private AssistantRepository assistantRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private SpecialUserService specialUserService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllSpecialPersons() {
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAllByRole(ROLE_ASSISTANT.name())).thenReturn(expectedUsers);

        List<User> actualUsers = specialUserService.findAllSpecialPersons();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAllByRole(ROLE_ASSISTANT.name());
    }

    @Test
    public void testAddNewSpecialUser_Success() {
        User user = new User();
        user.setId(1L);
        Assistant assistant = new Assistant();
        assistant.setId(2L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(assistantRepository.findById(anyLong())).thenReturn(Optional.of(assistant));
        when(specialUserRepository.existsByUserId(anyLong())).thenReturn(false);
        when(specialUserRepository.save(any(SpecialUser.class))).thenReturn(new SpecialUser());

        SpecialUser specialUser = specialUserService.addNewSpecialUser(1L, "Diabet", "Cronica", 2L);

        assertNotNull(specialUser);
        assertEquals(user, specialUser.getUser());
        assertEquals("Diabet", specialUser.getDisease());
        assertEquals("Cronica", specialUser.getDiseaseType());
        assertEquals(assistant, specialUser.getAssistant());

        verify(userRepository, times(1)).findById(1L);
        verify(assistantRepository, times(1)).findById(2L);
        verify(specialUserRepository, times(1)).existsByUserId(1L);
        verify(specialUserRepository, times(1)).save(any(SpecialUser.class));
    }

    @Test
    public void testAddNewSpecialUser_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            specialUserService.addNewSpecialUser(1L, "Diabet", "Cronica", 2L);
        });

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddNewSpecialUser_AssistantNotFound() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(assistantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            specialUserService.addNewSpecialUser(1L, "Diabet", "Cronica", 2L);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(assistantRepository, times(1)).findById(2L);
    }

    @Test
    public void testAddNewSpecialUser_ProfileExists() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(specialUserRepository.existsByUserId(anyLong())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            specialUserService.addNewSpecialUser(1L, "Diabet", "Cronica", 2L);
        });

        verify(userRepository, times(1)).findById(1L);
        verify(specialUserRepository, times(1)).existsByUserId(1L);
    }
    @Test
    public void testFindAllByRole() {
        List<User> expectedUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAllByRole(anyString())).thenReturn(expectedUsers);

        List<User> actualUsers = specialUserService.findAllByRole("ROLE_USER");

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAllByRole("ROLE_USER");
    }

    @Test
    public void testFindAll() {
        List<SpecialUser> expectedSpecialUsers = Arrays.asList(new SpecialUser(), new SpecialUser());
        when(specialUserRepository.findAll()).thenReturn(expectedSpecialUsers);

        List<SpecialUser> actualSpecialUsers = specialUserService.findAll();

        assertEquals(expectedSpecialUsers, actualSpecialUsers);
        verify(specialUserRepository, times(1)).findAll();
    }

    @Test
    public void testSaveManySpecialUsers() {
        // Similar to the previous tests, you can mock the dependencies and verify the behavior
        // Mock necessary methods and dependencies
        // ...
    }

    @Test
    public void testFindDoctorsBySpecialUserUsername() {
        SpecialUser specialUser = new SpecialUser();
        specialUser.setId(1L);
        List<Doctor> expectedDoctors = Arrays.asList(new Doctor(), new Doctor());

        when(specialUserRepository.findByUserUsername(anyString())).thenReturn(specialUser);
        when(doctorRepository.findBySpecialUserId(anyLong())).thenReturn(expectedDoctors);

        List<Doctor> actualDoctors = specialUserService.findDoctorsBySpecialUserUsername("testUser");

        assertEquals(expectedDoctors, actualDoctors);
        verify(specialUserRepository, times(1)).findByUserUsername("testUser");
        verify(doctorRepository, times(1)).findBySpecialUserId(1L);
    }

    @Test
    public void testFindAssistantBySpecialUserUsername() {
        SpecialUser specialUser = new SpecialUser();
        specialUser.setId(1L);
        Assistant expectedAssistant = new Assistant();

        when(specialUserRepository.findByUserUsername(anyString())).thenReturn(specialUser);
        when(assistantRepository.findBySpecialuser_Id(anyLong())).thenReturn(expectedAssistant);

        Assistant actualAssistant = specialUserService.findAssistantBySpecialUserUsername("testUser");

        assertEquals(expectedAssistant, actualAssistant);
        verify(specialUserRepository, times(1)).findByUserUsername("testUser");
        verify(assistantRepository, times(1)).findBySpecialuser_Id(1L);
    }
}
