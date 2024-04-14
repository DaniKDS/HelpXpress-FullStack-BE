package com.supportportal.service.inter;

import com.supportportal.domain.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAppointmentsByAssistantUsername(String assistantUsername);
}
