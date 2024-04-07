package com.supportportal.controller;


import com.supportportal.service.impl.AppointementServiceImpl;
import com.supportportal.service.inter.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}