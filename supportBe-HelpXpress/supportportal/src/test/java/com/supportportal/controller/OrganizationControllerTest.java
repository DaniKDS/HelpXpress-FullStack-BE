package com.supportportal.controller;

import com.supportportal.domain.Organization;
import com.supportportal.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrganizationControllerTest {

    @Mock
    private OrganizationServiceImpl organizationService;

    @InjectMocks
    private OrganizationController organizationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddManyOrganizationsSuccess() {
        doNothing().when(organizationService).addManyOrganizations();

        ResponseEntity<String> response = organizationController.addManyOrganizations();

        verify(organizationService, times(1)).addManyOrganizations();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("S-au adăugat cu succes 100 de organizații.", response.getBody());
    }

    @Test
    public void testAddManyOrganizationsFailure() {
        doThrow(new RuntimeException("Test Exception")).when(organizationService).addManyOrganizations();

        ResponseEntity<String> response = organizationController.addManyOrganizations();

        verify(organizationService, times(1)).addManyOrganizations();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("A apărut o eroare la adăugarea organizațiilor: Test Exception", response.getBody());
    }

    @Test
    public void testAddManySpecialUserOrganizationsSuccess() {
        doNothing().when(organizationService).addManySpecialUserOrganizations();

        ResponseEntity<String> response = organizationController.addManySpecialUserOrganizations();

        verify(organizationService, times(1)).addManySpecialUserOrganizations();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("S-au adăugat cu succes 100 de asocieri între SpecialUser și Organization.", response.getBody());
    }

    @Test
    public void testAddManySpecialUserOrganizationsFailure() {
        doThrow(new RuntimeException("Test Exception")).when(organizationService).addManySpecialUserOrganizations();

        ResponseEntity<String> response = organizationController.addManySpecialUserOrganizations();

        verify(organizationService, times(1)).addManySpecialUserOrganizations();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("A apărut o eroare la adăugarea asocierilor: Test Exception", response.getBody());
    }

    @Test
    public void testFindAllOrganizations() {
        List<Organization> organizations = Collections.singletonList(new Organization());
        when(organizationService.findAllOrganization()).thenReturn(organizations);

        ResponseEntity<List<Organization>> response = organizationController.findAllOrganizations();

        verify(organizationService, times(1)).findAllOrganization();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organizations, response.getBody());
    }

    @Test
    public void testFindOrganizationById() {
        Organization organization = new Organization();
        when(organizationService.findOrganizationById(anyLong())).thenReturn(organization);

        ResponseEntity<Organization> response = organizationController.findOrganizationById(1L);

        verify(organizationService, times(1)).findOrganizationById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(organization, response.getBody());
    }
}
