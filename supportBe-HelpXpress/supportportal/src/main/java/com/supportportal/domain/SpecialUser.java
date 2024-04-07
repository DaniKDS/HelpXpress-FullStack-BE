package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SpecialUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Aceasta este coloana cheie străină în tabela Assistant care referă tabela User
    private User user;

    private String disease;
    private String diseaseType;

    @OneToMany(mappedBy = "specialUser")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "specialUser")
    @JsonIgnore
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "specialUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Doctor> doctors;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assistant_id")
    private Assistant assistant;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnore
    @JoinTable(
        name = "specialuser_organization", // Name of the join table
        joinColumns = @JoinColumn(name = "special_user_id", referencedColumnName = "id"), // Foreign key for SpecialUser in join table
        inverseJoinColumns = @JoinColumn(name = "organization_id", referencedColumnName = "id") // Foreign key for Organization in join table
    )
    private List<Organization> organization = new ArrayList<>();

}
