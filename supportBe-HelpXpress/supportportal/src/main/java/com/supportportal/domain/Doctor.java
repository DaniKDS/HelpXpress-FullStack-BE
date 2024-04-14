package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Aceasta este coloana cheie străină în tabela Assistant care referă tabela User
    private User user;

    private Integer experienceYears;
    private String speciality;

    @ManyToOne
    @JoinColumn(name = "special_user_id") // Acesta va fi numele coloanei cheie străine în tabelul Doctor
    private SpecialUser specialUser;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Appointment> appointments;

}
