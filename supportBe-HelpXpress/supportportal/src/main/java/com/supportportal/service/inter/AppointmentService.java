package com.supportportal.service.inter;

import com.supportportal.domain.Appointment;

import javax.transaction.Transactional;
import java.util.List;

public interface AppointmentService {

    List<Appointment> findAllAppointments();
    Appointment findAppointmentById(Long appointmentId);
    List<Appointment> findAppointmentsByAssistantUsername(String assistantUsername);
    @Transactional
    Appointment addAppointment(Appointment appointment);
}
