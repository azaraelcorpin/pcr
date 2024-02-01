package com.spms.pcr.DataSource_PCR.model;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pcr_schedule")
public class PcrSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_start", nullable = false)
    private Date dateStart;

    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @Column(name = "status", nullable = false)
    private String status;

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_ACTIVE = "Active";

    @Transient //@transient - annotates that this attribute must not reflect to the database
    public static final String STATUS_INACTIVE = "Inactive";
}
