package com.supportportal.service.impl;

import com.supportportal.domain.Appointment;
import com.supportportal.domain.Organization;
import com.supportportal.domain.User;
import com.supportportal.repository.AppointmentRepository;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
@Service
public class AppointementServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public void addBulkAppointments() {
        List<User> users = userRepository.findAll();
        List<User> doctors = userRepository.findAll(); // Presupunem că doctorii sunt filtrați în mod adecvat
        List<Organization> organizations = organizationRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < 150; i++) {
            Appointment appointment = new Appointment();
//            appointment.setUser(users.get(random.nextInt(users.size())));
//            appointment.setDoctor(doctors.get(random.nextInt(doctors.size())));
            appointment.setOrganization(organizations.get(random.nextInt(organizations.size())));
            appointment.setAppointmentTime(new Date()); // Setează o dată și oră aleatorie sau specifică
            appointment.setStatus("Programat");
            appointment.setNotes("Note pentru programare " + i);

            appointmentRepository.save(appointment);
        }
    }
}