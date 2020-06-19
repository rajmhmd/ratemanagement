package com.raj.logistics.ratemanagement.mapper;

import com.raj.logistics.ratemanagement.dto.Ratewithsurcharge;
import com.raj.logistics.ratemanagement.dto.Surcharge;
import com.raj.logistics.ratemanagement.entity.Rate;

public class RateManagementMapper {

    public static Ratewithsurcharge mapRatewithsurcharge(Rate rate, Surcharge surcharge) {
        Ratewithsurcharge ratewithsurcharge = null;
        if(null != rate && null != surcharge) {
            ratewithsurcharge = new Ratewithsurcharge();
            ratewithsurcharge.setRateId(rate.getRateId());
            ratewithsurcharge.setAmount(rate.getAmount());
            ratewithsurcharge.setRateDesc(rate.getRateDesc());
            ratewithsurcharge.setRateEffectiveDate(rate.getRateEffectiveDate());
            ratewithsurcharge.setRateExpirationDate(rate.getRateExpirationDate());
            ratewithsurcharge.setSurchargeRate(surcharge.getSurchargeRate());
            ratewithsurcharge.setSurchargeDescription(surcharge.getSurchargeDescription());
        }
        return ratewithsurcharge;
    }
}
