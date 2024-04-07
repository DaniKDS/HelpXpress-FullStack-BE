package com.supportportal.service.impl;

import com.supportportal.domain.Appointment;
import com.supportportal.domain.Organization;
import com.supportportal.domain.User;
import com.supportportal.repository.AppointmentRepository;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.repository.users.UserRepository;
import com.supportportal.service.inter.AppointmentService;
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
    private SpecialUserRepository specialUserRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    private Random random = new Random();

    public void saveManyAppointments() {
        List<Long> specialUserIds = specialUserRepository.findAllIds();
        List<Long> organizationIds = organizationRepository.findAllIds();
        List<Long> doctorIds = doctorRepository.findAllIds();
        List<Long> assistantIds = assistantRepository.findAllIds();

        for (int i = 0; i < 300; i++) {
            Long specialUserId = pickRandomValidId(specialUserIds);
            Long organizationId = pickRandomValidId(organizationIds);
            Long doctorId = pickRandomValidId(doctorIds);
            Long assistantId = pickRandomValidId(assistantIds);

            // Verifică dacă toate ID-urile sunt valide
            if (specialUserId == null || organizationId == null || doctorId == null || assistantId == null) {
                continue; // Sari peste această iterație dacă oricare dintre ID-uri nu este valid
            }

            Date appointmentTime = generateRandomAppointmentTime();
            Date appointmentEndTime = new Date(appointmentTime.getTime() + (random.nextInt(2) + 1) * 3600000); // 1-2 ore adăugate la appointmentTime

            Appointment appointment = new Appointment();
            appointment.setSpecialUserId(specialUserId);
            appointment.setOrganizationId(organizationId);
            appointment.setDoctorId(doctorId);
            appointment.setAssistantId(assistantId);
            appointment.setAppointmentTime(appointmentTime);
            appointment.setAppointmentEndTime(appointmentEndTime);
            appointment.setStatus("programată");
            appointment.setNotes("Generat automat de sistem");

            appointmentRepository.save(appointment);
        }
    }

    private Date generateRandomAppointmentTime() {
        // Logica pentru generarea unui timp aleatoriu între 24 de ore și 7 zile în viitor
        long randomMillisInFuture = System.currentTimeMillis() + ((long) (24 + random.nextInt(168)) * 3600000);
        return new Date(randomMillisInFuture);
    }

    private Long pickRandomValidId(List<Long> ids) {
        if (ids.isEmpty()) {
            return null;
        }
        Long id = ids.get(random.nextInt(ids.size()));
        return id;
    }
}