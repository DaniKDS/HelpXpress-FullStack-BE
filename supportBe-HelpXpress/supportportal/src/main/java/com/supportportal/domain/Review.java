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
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "special_user_id")
    private SpecialUser specialUser;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "organization_id")
    private Organization organization;

    private String comment;
    private Integer rating;

    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    public void setOrganizationId(Long organizationId) {
        setOrganization(Organization.builder().id(organizationId).build());
    }
    public void setDoctorId(Long doctorId) {
        setDoctor(Doctor.builder().id(doctorId).build());
    }
    public void setAssistantId(Long assistantId) {
        setAssistant(Assistant.builder().id(assistantId).build());
    }
    public void setSpecialUserId(Long l) {
        setSpecialUser(SpecialUser.builder().id(l).build());
    }

}