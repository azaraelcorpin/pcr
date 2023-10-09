package com.spms.pcr.DataSource_PCR.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "office")
public class Office {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "top_office", nullable = true)
    private Long topOffice;

    @Column(name = "is_sector", nullable = false)
    private Boolean isSector;    
}
