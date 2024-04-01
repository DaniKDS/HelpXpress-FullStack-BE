package com.supportportal.controller;

import com.supportportal.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/addBulkAppointments")
    public ResponseEntity<String> addBulkAppointments() {
        appointmentService.addBulkAppointments();
        return ResponseEntity.ok("150 de programări au fost adăugate cu succes.");
    }
}
