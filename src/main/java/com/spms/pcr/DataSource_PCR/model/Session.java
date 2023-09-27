package com.spms.pcr.DataSource_PCR.model;

import java.util.Date;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "session")
public class Session {
    
    @Id
    private String email;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "_date", nullable = false)
    private Date date;

}
