package com.supportportal.repository;

import com.supportportal.domain.Appointment;
import com.supportportal.domain.SpecialUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // În AppointmentRepository
    List<Appointment> findBySpecialUserUserUsername(String username);
}
