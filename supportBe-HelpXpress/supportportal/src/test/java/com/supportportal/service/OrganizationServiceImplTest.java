package com.supportportal.service;

import com.supportportal.domain.Organization;
import com.supportportal.domain.SpecialUser;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrganizationServiceImplTest {
    @Mock
    private SpecialUserRepository specialUserRepository;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindOrganizationsByUsername() {
        SpecialUser specialUser = new SpecialUser();
        Organization organization = new Organization();
        specialUser.setOrganization(List.of(organization));
        when(specialUserRepository.findByUserUsername(anyString())).thenReturn(specialUser);

        List<Organization> organizations = organizationService.findOrganizationsByUsername("testuser");

        assertNotNull(organizations);
        assertEquals(1, organizations.size());
        verify(specialUserRepository, times(1)).findByUserUsername("testuser");
    }

    @Test
    public void testFindOrganizationsByUsername_UserNotFound() {
        when(specialUserRepository.findByUserUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            organizationService.findOrganizationsByUsername("testuser");
        });

        verify(specialUserRepository, times(1)).findByUserUsername("testuser");
    }
}
