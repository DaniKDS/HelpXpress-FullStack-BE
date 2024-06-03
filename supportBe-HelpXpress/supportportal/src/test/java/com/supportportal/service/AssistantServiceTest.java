package com.supportportal.service;

import com.supportportal.domain.Assistant;
import com.supportportal.domain.SpecialUser;
import com.supportportal.domain.User;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.UserRepository;
import com.supportportal.service.users.AssistantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AssistantServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AssistantRepository assistantRepository;

    @InjectMocks
    private AssistantService assistantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllAssistants() {
        List<Assistant> assistants = Arrays.asList(new Assistant(), new Assistant());
        when(assistantRepository.findAll()).thenReturn(assistants);

        List<Assistant> result = assistantService.findAllAssistants();

        assertEquals(assistants, result);
        verify(assistantRepository, times(1)).findAll();
    }

    @Test
    public void testSaveManyAssistants() {
        List<User> usersWithRoleAssistant = Arrays.asList(new User(), new User());
        when(userRepository.findAllByRole(anyString())).thenReturn(usersWithRoleAssistant);
        when(assistantRepository.existsByUserId(anyLong())).thenReturn(false);

        assistantService.saveManyAssistants();

        verify(userRepository, times(1)).findAllByRole("ROLE_ASSISTANT");
        verify(assistantRepository, times(usersWithRoleAssistant.size())).save(any(Assistant.class));
    }

    @Test
    public void testFindAssistantById() {
        Assistant assistant = new Assistant();
        when(assistantRepository.findById(anyLong())).thenReturn(Optional.of(assistant));

        Assistant result = assistantService.findAssistantById(1L);

        assertEquals(assistant, result);
        verify(assistantRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAssistantById_NotFound() {
        when(assistantRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assistant result = assistantService.findAssistantById(1L);

        assertNull(result);
        verify(assistantRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAssistantByUserId() {
        Assistant assistant = new Assistant();
        when(assistantRepository.findByUserId(anyLong())).thenReturn(Optional.of(assistant));

        Assistant result = assistantService.findAssistantByUserId(1L);

        assertEquals(assistant, result);
        verify(assistantRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testFindAssistantByUserId_NotFound() {
        when(assistantRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        Assistant result = assistantService.findAssistantByUserId(1L);

        assertNull(result);
        verify(assistantRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testFindSpecialUserByAssistantUsername() {
        Assistant assistant = new Assistant();
        SpecialUser specialUser = new SpecialUser();
        assistant.setSpecialuser(specialUser);
        when(assistantRepository.findByUserUsername(anyString())).thenReturn(Optional.of(assistant));

        SpecialUser result = assistantService.findSpecialUserByAssistantUsername("assistantUsername");

        assertEquals(specialUser, result);
        verify(assistantRepository, times(1)).findByUserUsername("assistantUsername");
    }

    @Test
    public void testFindSpecialUserByAssistantUsername_NotFound() {
        when(assistantRepository.findByUserUsername(anyString())).thenReturn(Optional.empty());

        SpecialUser result = assistantService.findSpecialUserByAssistantUsername("assistantUsername");

        assertNull(result);
        verify(assistantRepository, times(1)).findByUserUsername("assistantUsername");
    }
}
