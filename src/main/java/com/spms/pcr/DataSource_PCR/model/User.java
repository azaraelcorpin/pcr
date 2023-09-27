package com.spms.pcr.DataSource_PCR.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    
    @Id
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "privileges")
    private String privileges;
        
    @Column(name = "office_id")
    private int officeId;

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_ACTIVE = "Active";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_INACTIVE = "Inactive";
}
