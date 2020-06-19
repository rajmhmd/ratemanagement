package com.raj.logistics.ratemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @Id
    private Long rateId;

    @Column(nullable=true)
    private String rateDesc;

    @Column(nullable=false)
    private Date rateEffectiveDate;

    @Column(nullable=false)
    private Date rateExpirationDate;

    @Column(nullable=false)
    private Integer amount;

}
