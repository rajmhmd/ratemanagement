package com.raj.logistics.ratemanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratewithsurcharge {

    private Long rateId;

    private String rateDesc;

    private Date rateEffectiveDate;

    private Date rateExpirationDate;

    private Integer amount;

    private Integer surchargeRate;

    private String surchargeDescription;

}
