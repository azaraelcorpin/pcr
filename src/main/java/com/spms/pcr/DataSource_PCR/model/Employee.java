package com.spms.pcr.DataSource_PCR.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lname", nullable = false)
    private String lname;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "mname", nullable = true)
    private String mname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "office_id", nullable = true)
    private Long officeId;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "pcr_type", nullable = false)
    private String pcrType;

    @Column(name = "status", nullable = false)
    private String status;  
}
