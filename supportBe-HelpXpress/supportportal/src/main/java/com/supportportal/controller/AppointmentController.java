package com.supportportal.controller;


import com.supportportal.domain.Appointment;
import com.supportportal.service.impl.AppointementServiceImpl;
import com.supportportal.service.inter.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private final AppointementServiceImpl appointmentService;
    @Autowired
    public AppointmentController(AppointementServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/saveManyAppointments")
    public ResponseEntity<String> saveManyAppointments() {
        try {
            appointmentService.saveManyAppointments();
            return new ResponseEntity<>("Programările au fost create cu succes.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("A apărut o eroare la crearea programărilor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/allAppointments")
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    @GetMapping("/allAppointments/{appointmentId}")
    public ResponseEntity<Appointment> findAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.findAppointmentById(appointmentId));
    }

    @GetMapping("/findBySpecialUserUsername/{username}")
    public ResponseEntity<List<Appointment>> getAppointmentsBySpecialUserUsername(@PathVariable String username) {
        List<Appointment> appointments = appointmentService.findBySpecialUserUsername(username);
        if(appointments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

}