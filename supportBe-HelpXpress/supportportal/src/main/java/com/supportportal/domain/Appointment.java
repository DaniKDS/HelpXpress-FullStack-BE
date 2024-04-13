package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JoinColumn(name = "special_user_id") // Folosește un nume de coloană care reprezintă cheia străină
    private SpecialUser specialUser;

    @ManyToOne
    @JoinColumn(name = "doctor_id") // Folosește un nume de coloană care reprezintă cheia străină
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "organization_id") // Folosește un nume de coloană care reprezintă cheia străină
    private Organization organization;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assistant_id") // Folosește un nume de coloană care reprezintă cheia străină
    private Assistant assistant;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentEndTime;

    private String status;
    private String notes;

    public void setSpecialUserId(Long userId) {
        setSpecialUser(SpecialUser.builder().id(userId).build());
    }
    public void setOrganizationId(Long organizationId) {
        setOrganization(Organization.builder().id(organizationId).build());
    }
    public void setDoctorId(Long doctorId) {
        setDoctor(Doctor.builder().id(doctorId).build());
    }
    public void setAssistantId(Long assistantId) {
        setAssistant(Assistant.builder().id(assistantId).build());
    }
}