package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String type;
    private String address;
    private String description;
    private String phone;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "organization")
    @JsonIgnore
    private List<Appointment> appointments;

    @ManyToMany(mappedBy = "organizations", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<SpecialUser> specialUsers = new ArrayList<>();
}