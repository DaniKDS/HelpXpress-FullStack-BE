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
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String problemType;
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization; // Opțional
}