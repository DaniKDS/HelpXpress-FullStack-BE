package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}, ROLE_DOCTOR{read, edit}, ROLE_ASSISTANT
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;
    private Integer age;
    private String phone;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String gender;

    // Relația Many-to-Many cu Benzinarie
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_benzinarie",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "benzinarie_id")
    )
    private List<GazStation> benzinarii;
}
