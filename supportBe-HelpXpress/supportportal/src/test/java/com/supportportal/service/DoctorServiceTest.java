package com.supportportal.service;

import com.supportportal.domain.Appointment;
import com.supportportal.domain.Doctor;
import com.supportportal.domain.SpecialUser;
import com.supportportal.repository.AppointmentRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.service.users.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DoctorServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllDoctors() {
        List<Doctor> doctors = Arrays.asList(new Doctor(), new Doctor());
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.findAllDoctors();

        assertEquals(doctors, result);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    public void testFindDoctorById() {
        Doctor doctor = new Doctor();
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.findDoctorById(1L);

        assertEquals(doctor, result);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindDoctorById_NotFound() {
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.empty());

        Doctor result = doctorService.findDoctorById(1L);

        assertNull(result);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindSpecialUserByDoctorUsername() {
        Doctor doctor = new Doctor();
        SpecialUser specialUser = new SpecialUser();
        doctor.setSpecialUser(specialUser);
        when(doctorRepository.findByUserUsername(anyString())).thenReturn(doctor);

        SpecialUser result = doctorService.findSpecialUserByDoctorUsername("doctorUsername");

        assertEquals(specialUser, result);
        verify(doctorRepository, times(1)).findByUserUsername("doctorUsername");
    }

    @Test
    public void testFindSpecialUserByDoctorUsername_NotFound() {
        when(doctorRepository.findByUserUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> doctorService.findSpecialUserByDoctorUsername("doctorUsername"));
        verify(doctorRepository, times(1)).findByUserUsername("doctorUsername");
    }

    @Test
    public void testFindAppointmentsByDoctorUsername() {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        when(appointmentRepository.findAppointmentsByDoctorUsername(anyString())).thenReturn(appointments);

        List<Appointment> result = doctorService.findAppointmentsByDoctorUsername("doctorUsername");

        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findAppointmentsByDoctorUsername("doctorUsername");
    }
}
