package com.supportportal.controller;

import com.supportportal.controller.users.UserController;
import com.supportportal.domain.Http.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.domain.principal.UserPrincipal;
import com.supportportal.exception.domain.UsernameExistException;
import com.supportportal.service.inter.UserService;
import com.supportportal.utility.JWTTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JWTTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        UserPrincipal userPrincipal = new UserPrincipal(user);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userService.findUserByUsername(anyString())).thenReturn(user);
        when(jwtTokenProvider.generateJwtToken(any(UserPrincipal.class))).thenReturn("token");

        SecurityContextHolder.setContext(securityContext);
        ResponseEntity<User> response = userController.login(user);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).findUserByUsername(anyString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals("token", response.getHeaders().get("JWT-Token").get(0));
    }

    @Test
    public void testLoginUserNotFound() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("User not found"));

        try {
            userController.login(user);
        } catch (RuntimeException e) {
            assertEquals("User not found", e.getMessage());
        }

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(0)).findUserByUsername(anyString());
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userService.register(anyString(), anyString(), anyString(), anyString())).thenReturn(user);

        ResponseEntity<User> response = userController.register(user);

        verify(userService, times(1)).register(anyString(), anyString(), anyString(), anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testRegisterUsernameExistException() throws Exception {
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userService.register(anyString(), anyString(), anyString(), anyString())).thenThrow(new UsernameExistException("Username exists"));

        try {
            userController.register(user);
        } catch (UsernameExistException e) {
            assertEquals("Username exists", e.getMessage());
        }

        verify(userService, times(1)).register(anyString(), anyString(), anyString(), anyString());
    }
    @Test
    public void testAddNewUserSuccess() throws Exception {
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userService.addNewUser(
            eq("First"),
            eq("Last"),
            eq("testUser"),
            eq("test@example.com"),
            eq("ROLE_USER"),
            eq(true),
            eq(true),
            isNull(MultipartFile.class)
        )).thenReturn(user);

        ResponseEntity<User> response = userController.addNewUser(
            "First", "Last", "testUser", "test@example.com", "ROLE_USER", "true", "true", null);

        verify(userService, times(1)).addNewUser(
            eq("First"),
            eq("Last"),
            eq("testUser"),
            eq("test@example.com"),
            eq("ROLE_USER"),
            eq(true),
            eq(true),
            isNull(MultipartFile.class)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testUpdateUserSuccess() throws Exception {
        User user = new User();
        user.setFirstName("First");
        user.setLastName("Last");
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        when(userService.updateUser(
            eq("currentUsername"),
            eq("First"),
            eq("Last"),
            eq("testUser"),
            eq("test@example.com"),
            eq("Male"),
            eq("1234567890"),
            eq("ROLE_USER"),
            eq(true),
            eq(true),
            isNull(MultipartFile.class)
        )).thenReturn(user);

        ResponseEntity<User> response = userController.update(
            "currentUsername", "First", "Last", "testUser", "test@example.com", "Male", "1234567890", "ROLE_USER", "true", "true", null);

        verify(userService, times(1)).updateUser(
            eq("currentUsername"),
            eq("First"),
            eq("Last"),
            eq("testUser"),
            eq("test@example.com"),
            eq("Male"),
            eq("1234567890"),
            eq("ROLE_USER"),
            eq(true),
            eq(true),
            isNull(MultipartFile.class)
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }


    @Test
    public void testGetUserSuccess() {
        User user = new User();
        user.setUsername("testUser");

        when(userService.findUserByUsername(anyString())).thenReturn(user);

        ResponseEntity<User> response = userController.getUser("testUser");

        verify(userService, times(1)).findUserByUsername(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetAllUsersSuccess() {
        List<User> users = List.of(new User(), new User());

        when(userService.getUsers()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        verify(userService, times(1)).getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testResetPasswordSuccess() throws Exception {
        String email = "test@example.com";

        doNothing().when(userService).resetPassword(anyString());

        ResponseEntity<HttpResponse> response = userController.resetPassword(email);

        verify(userService, times(1)).resetPassword(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("An email with a new password was sent to: " + email, response.getBody().getMessage());
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        String username = "testUser";

        doNothing().when(userService).deleteUser(anyString());

        ResponseEntity<HttpResponse> response = userController.deleteUser(username);

        verify(userService, times(1)).deleteUser(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody().getMessage());
    }

    @Test
    public void testUpdateProfileImageSuccess() throws Exception {
        User user = new User();
        user.setUsername("testUser");

        when(userService.updateProfileImage(eq("testUser"), isNull(MultipartFile.class))).thenReturn(user);

        ResponseEntity<User> response = userController.updateProfileImage("testUser", null);

        verify(userService, times(1)).updateProfileImage(eq("testUser"), isNull(MultipartFile.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
}
