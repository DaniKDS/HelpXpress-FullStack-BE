package com.supportportal.repository;

import com.supportportal.domain.Appointment;
import com.supportportal.domain.SpecialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // În AppointmentRepository
    List<Appointment> findBySpecialUserUserUsername(String username);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d WHERE d.user.username = :username")
    List<Appointment> findAppointmentsByDoctorUsername(@Param("username") String username);
}
