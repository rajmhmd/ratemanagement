package com.raj.logistics.ratemanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Surcharge {

    private Integer surchargeRate;

    private String surchargeDescription;
}
