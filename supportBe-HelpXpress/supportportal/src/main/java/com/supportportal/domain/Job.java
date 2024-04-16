package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Job implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name; // numele jobului
    private String description; // descrierea jobului

    // Informații detaliate pentru filtrare
    private String region; // regiunea pentru job
    private String county; // județul pentru job
    private String city; // orașul pentru job

    private String accessibilityFeatures; // caracteristici de accesibilitate

    // Specific pentru persoanele cu dizabilități
    private boolean isDedicatedForDisability; // dacă jobul este dedicat persoanelor cu dizabilități
    private String disabilityType; // tipul de dizabilitate acceptat pentru job

    // Alte informații relevante
    private String employmentType; // tipul de angajare (full-time, part-time, contract, temporar, etc.)
    private String salaryRange; // intervalul salarial

    @Temporal(TemporalType.DATE)
    private Date postingDate; // data la care jobul a fost postat

    private boolean isRemote; // dacă jobul poate fi efectuat la distanță

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String employerId; // ID-ul angajatorului care a postat jobul

    private String employerName; // numele angajatorului
    private String employerPhone; // numărul de telefon al angajatorului

    // Metadate, cum ar fi statusul jobului (activ/inactiv)
    private String status;
}
