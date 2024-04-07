package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Assistant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Aceasta este coloana cheie străină în tabela Assistant care referă tabela User
    private User user;

    private Integer experienceYears;
    private String speciality;
    private String grade;
    @OneToMany(mappedBy = "assistant")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "assistant")
    @JsonIgnore
    private List<Appointment> appointments;

    @OneToOne(mappedBy = "assistant", cascade = CascadeType.ALL)
    @JsonIgnore
    private SpecialUser specialuser;

}