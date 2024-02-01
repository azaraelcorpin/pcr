package com.spms.pcr.DataSource_PCR.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "office_employee")
public class OfficeEmployee {
    
    @Id
    @Column(name = "office_id")
    private Long officeId;

    
    @Column(name = "emp_id")
    private Long empId;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "roles", nullable = false)
    private String roles;

}
