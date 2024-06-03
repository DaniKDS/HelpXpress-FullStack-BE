package com.supportportal.service.impl;

import com.supportportal.domain.Appointment;
import com.supportportal.repository.AppointmentRepository;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.service.inter.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AppointementServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private SpecialUserRepository specialUserRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AssistantRepository assistantRepository;

    @InjectMocks
    private AppointementServiceImpl appointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveManyAppointments() {
        List<Long> specialUserIds = Arrays.asList(1L, 2L, 3L);
        List<Long> organizationIds = Arrays.asList(1L, 2L, 3L);
        List<Long> doctorIds = Arrays.asList(1L, 2L, 3L);
        List<Long> assistantIds = Arrays.asList(1L, 2L, 3L);

        when(specialUserRepository.findAllIds()).thenReturn(specialUserIds);
        when(organizationRepository.findAllIds()).thenReturn(organizationIds);
        when(doctorRepository.findAllIds()).thenReturn(doctorIds);
        when(assistantRepository.findAllIds()).thenReturn(assistantIds);

        appointmentService.saveManyAppointments();

        verify(appointmentRepository, atLeast(1)).save(any(Appointment.class));
    }

    @Test
    public void testFindAllAppointments() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.findAllAppointments();

        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    public void testFindAppointmentById() {
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.findAppointmentById(1L);

        assertEquals(appointment, result);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAppointmentById_NotFound() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Appointment result = appointmentService.findAppointmentById(1L);

        assertNull(result);
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindBySpecialUserUsername() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findBySpecialUserUserUsername(anyString())).thenReturn(appointments);

        List<Appointment> result = appointmentService.findBySpecialUserUsername("username");

        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findBySpecialUserUserUsername("username");
    }

    @Test
    public void testFindAppointmentsByAssistantUsername() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findAppointmentsByAssistantUsername(anyString())).thenReturn(appointments);

        List<Appointment> result = appointmentService.findAppointmentsByAssistantUsername("assistantUsername");

        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findAppointmentsByAssistantUsername("assistantUsername");
    }
}
