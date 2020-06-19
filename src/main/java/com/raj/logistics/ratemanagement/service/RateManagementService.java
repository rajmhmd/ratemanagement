package com.raj.logistics.ratemanagement.service;

import static com.raj.logistics.ratemanagement.common.RateManagementConstants.surcharge_default_desc;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.surcharge_default_rate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.raj.logistics.ratemanagement.dto.Ratewithsurcharge;
import com.raj.logistics.ratemanagement.dto.Surcharge;
import com.raj.logistics.ratemanagement.entity.Rate;
import com.raj.logistics.ratemanagement.mapper.RateManagementMapper;
import com.raj.logistics.ratemanagement.repository.RateManagementRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateManagementService {

    private final RateManagementRepository rateManagementRepository;

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${rms.surcharge.serviceUrl}")
    String surchargeServiceUrl;

    @HystrixCommand(fallbackMethod = "getDefaultRatewithSurchargeById")
    public Ratewithsurcharge getRatewithSurchargeById(final Long id) {
        Optional<Rate> rate = getRateById(id);
        if (rate.isPresent()) {
            Surcharge surcharge = getSurchargeFromRestService();
            if (null != surcharge) {
                return RateManagementMapper.mapRatewithsurcharge(rate.get(), surcharge);
            }
        }
        return null;
    }

    public Optional<Rate> getRateById(final Long id) {
        return rateManagementRepository.findById(id);
    }


    public Surcharge getSurchargeFromRestService() {
        log.info("surchargeServiceUrl : "+surchargeServiceUrl);
        Surcharge surcharge = restTemplate.getForObject(surchargeServiceUrl, Surcharge.class);
        if(null != surcharge)
            log.info("surcharge amount : "+surcharge.getSurchargeRate());
        return surcharge;
    }

    public Ratewithsurcharge getDefaultRatewithSurchargeById(final Long id) {
        Optional<Rate> rate = getRateById(id);
        if (rate.isPresent()) {
            Surcharge surcharge = getDefaultSurcharge();
            if (null != surcharge) {
                return RateManagementMapper.mapRatewithsurcharge(rate.get(), surcharge);
            }
        }
        return null;
    }

    public Surcharge getDefaultSurcharge() {
        Surcharge surcharge = new Surcharge();
        surcharge.setSurchargeRate(surcharge_default_rate);
        surcharge.setSurchargeDescription(surcharge_default_desc);
        return surcharge;
    }

    public Rate saveRate(final Rate rate) {
        return rateManagementRepository.save(rate);
    }

    public void deleteRate(final Long id) {
        rateManagementRepository.deleteById(id);
    }

}
