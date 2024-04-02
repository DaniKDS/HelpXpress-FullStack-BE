package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Referință opțională către doctor, dacă programarea este cu un doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor; // Presupunem că doctorii sunt și ei stocați în tabela `User` cu un rol specific

    // Referință opțională către o organizație, dacă programarea este cu o organizație
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;

    private String status;
    private String notes;

}
