package com.supportportal.controller;

import com.supportportal.domain.Appointment;
import com.supportportal.service.impl.AppointementServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AppointmentControllerTest {

    @Mock
    private AppointementServiceImpl appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveManyAppointmentsSuccess() {
        doNothing().when(appointmentService).saveManyAppointments();

        ResponseEntity<String> response = appointmentController.saveManyAppointments();

        verify(appointmentService, times(1)).saveManyAppointments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Programările au fost create cu succes.", response.getBody());
    }

    @Test
    public void testSaveManyAppointmentsFailure() {
        doThrow(new RuntimeException("Test exception")).when(appointmentService).saveManyAppointments();

        ResponseEntity<String> response = appointmentController.saveManyAppointments();

        verify(appointmentService, times(1)).saveManyAppointments();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("A apărut o eroare la crearea programărilor: Test exception", response.getBody());
    }

    @Test
    public void testFindAllAppointments() {
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.findAllAppointments()).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = appointmentController.findAllAppointments();

        verify(appointmentService, times(1)).findAllAppointments();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }

    @Test
    public void testFindAppointmentById() {
        Appointment appointment = new Appointment();
        when(appointmentService.findAppointmentById(anyLong())).thenReturn(appointment);

        ResponseEntity<Appointment> response = appointmentController.findAppointmentById(1L);

        verify(appointmentService, times(1)).findAppointmentById(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());
    }

    @Test
    public void testGetAppointmentsBySpecialUserUsernameNoContent() {
        when(appointmentService.findBySpecialUserUsername(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Appointment>> response = appointmentController.getAppointmentsBySpecialUserUsername("testUser");

        verify(appointmentService, times(1)).findBySpecialUserUsername(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAppointmentsBySpecialUserUsernameSuccess() {
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.findBySpecialUserUsername(anyString())).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = appointmentController.getAppointmentsBySpecialUserUsername("testUser");

        verify(appointmentService, times(1)).findBySpecialUserUsername(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }

    @Test
    public void testGetAppointmentsByAssistantUsernameNoContent() {
        when(appointmentService.findAppointmentsByAssistantUsername(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Appointment>> response = appointmentController.getAppointmentsByAssistantUsername("assistantUser");

        verify(appointmentService, times(1)).findAppointmentsByAssistantUsername(anyString());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAppointmentsByAssistantUsernameSuccess() {
        List<Appointment> appointments = Collections.singletonList(new Appointment());
        when(appointmentService.findAppointmentsByAssistantUsername(anyString())).thenReturn(appointments);

        ResponseEntity<List<Appointment>> response = appointmentController.getAppointmentsByAssistantUsername("assistantUser");

        verify(appointmentService, times(1)).findAppointmentsByAssistantUsername(anyString());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }

    @Test
    public void testGetAppointmentsByAssistantUsernameFailure() {
        when(appointmentService.findAppointmentsByAssistantUsername(anyString())).thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<Appointment>> response = appointmentController.getAppointmentsByAssistantUsername("assistantUser");

        verify(appointmentService, times(1)).findAppointmentsByAssistantUsername(anyString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
