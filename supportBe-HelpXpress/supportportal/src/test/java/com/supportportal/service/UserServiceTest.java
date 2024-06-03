package com.supportportal.service;

import com.supportportal.domain.User;
import com.supportportal.domain.principal.UserPrincipal;
import com.supportportal.exception.domain.EmailExistException;
import com.supportportal.exception.domain.EmailNotFoundException;
import com.supportportal.exception.domain.NotAnImageFileException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.repository.users.UserRepository;
import com.supportportal.service.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private LoginAttemptService loginAttemptService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ServletUriComponentsBuilder mockBuilder = mock(ServletUriComponentsBuilder.class);
        when(mockBuilder.path(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.toUriString()).thenReturn("http://localhost/default/user/image/testUser");
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("testUser");

        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername("testUser");

        assertNotNull(userPrincipal);
        assertEquals("testUser", userPrincipal.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findUserByUsername("testUser")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testUser");
        });

        assertEquals("No user found by username: testUser", exception.getMessage());
    }
    @Test
    public void testUpdateUser_Success() throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = new User();
        user.setUsername("currentUsername");
        when(userRepository.findUserByUsername("currentUsername")).thenReturn(user);
        when(userRepository.findUserByUsername("newUsername")).thenReturn(null);
        when(userRepository.findUserByEmail("newEmail@example.com")).thenReturn(null);

        User updatedUser = userService.updateUser("currentUsername", "First", "Last", "newUsername", "newEmail@example.com", "Male", "1234567890", "ROLE_USER", true, true, null);

        assertNotNull(updatedUser);
        assertEquals("newUsername", updatedUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testResetPassword_UserNotFound() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(null);

        Exception exception = assertThrows(EmailNotFoundException.class, () -> {
            userService.resetPassword("test@example.com");
        });

        assertEquals("No user found for email: test@example.com", exception.getMessage());
    }

    @Test
    public void testResetPassword_Success() throws EmailNotFoundException, MessagingException {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);

        userService.resetPassword("test@example.com");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        User result = userService.findUserByUsername("testUser");

        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testDeleteUser_Success() throws IOException {
        User user = new User();
        user.setUsername("testUser");
        user.setId(1L);
        when(userRepository.findUserByUsername("testUser")).thenReturn(user);

        userService.deleteUser("testUser");

        verify(userRepository, times(1)).deleteById(1L);
    }
}
