package com.supportportal.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GazStation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nume;
    private String locatie;
    private String brand; // De exemplu, "MOL", "ROMPETROL", etc.

    private String telefon;   // Câmp nou pentru numărul de telefon al benzinăriei
    private String email;

    @ManyToMany(mappedBy = "benzinarii")
    private List<User> users;
}
