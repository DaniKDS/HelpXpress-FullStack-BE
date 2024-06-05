package com.supportportal.controller;


import com.supportportal.domain.*;
import com.supportportal.repository.OrganizationRepository;
import com.supportportal.repository.users.AssistantRepository;
import com.supportportal.repository.users.DoctorRepository;
import com.supportportal.repository.users.SpecialUserRepository;
import com.supportportal.service.impl.AppointementServiceImpl;
import com.supportportal.service.inter.AppointmentService;
import lombok.Getter;
import lombok.Setter;
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

    @Autowired
    private SpecialUserRepository specialUserRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AssistantRepository assistantRepository;

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

    @GetMapping("/appointmentsByAssistant/{assistantUsername}")
    public ResponseEntity<List<Appointment>> getAppointmentsByAssistantUsername(@PathVariable String assistantUsername) {
        try {
            List<Appointment> appointments = appointmentService.findAppointmentsByAssistantUsername(assistantUsername);
            if (appointments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/data/all")
    public ResponseEntity<DataResponse> getAllData() {
        List<SpecialUser> specialUsers = specialUserRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();
        List<Organization> organizations = organizationRepository.findAll();
        List<Assistant> assistants = assistantRepository.findAll();

        DataResponse dataResponse = new DataResponse(specialUsers, doctors, organizations, assistants);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    // Endpoint pentru adăugarea unei noi programări
    @PostMapping("/add")
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.addAppointment(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }
}

@Getter
@Setter
class DataResponse {
    // Getteri și setteri
    private List<SpecialUser> specialUsers;
    private List<Doctor> doctors;
    private List<Organization> organizations;
    private List<Assistant> assistants;

    public DataResponse(List<SpecialUser> specialUsers, List<Doctor> doctors, List<Organization> organizations, List<Assistant> assistants) {
        this.specialUsers = specialUsers;
        this.doctors = doctors;
        this.organizations = organizations;
        this.assistants = assistants;
    }
}