package com.raj.logistics.ratemanagement.controller;

import static com.raj.logistics.ratemanagement.common.RateManagementConstants.FAILURE;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.MESSAGE;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.RATEID_NOT_FOUND_MSG;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.STATUS;
import static com.raj.logistics.ratemanagement.common.RateManagementConstants.SUCCESS;

import com.raj.logistics.ratemanagement.dto.Ratewithsurcharge;
import com.raj.logistics.ratemanagement.entity.Rate;
import com.raj.logistics.ratemanagement.service.RateManagementService;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ratemanagement")
@Slf4j
public class RateManagementController {

    private final RateManagementService rateManagementService;

    private static Map<String, String> successMap = new HashMap<>();
    private static Map<String, String> failureMap = new HashMap<>();
    private static Map<String, String> validationMap = new HashMap<>();

    static {
        successMap.put(STATUS, SUCCESS);
        failureMap.put(STATUS, FAILURE);
    }

    @PostMapping(value = "/saveRate",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveRate(@RequestBody Rate rate) {
        try {
            Rate returnObj = rateManagementService.saveRate(rate);
            return ResponseEntity.created(URI.create("/")).body(returnObj);
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failureMap);
        }
    }

    @GetMapping(value = "/searchRate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchRate(@PathVariable Long id) {
        try {
            Ratewithsurcharge ratewithsurcharge = rateManagementService.getRatewithSurchargeById(id);
            if (null != ratewithsurcharge) {
                return ResponseEntity.status(HttpStatus.OK).body(ratewithsurcharge);
            } else {
                log.error(RATEID_NOT_FOUND_MSG + id);
                validationMap.put(MESSAGE, RATEID_NOT_FOUND_MSG);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validationMap);
            }
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failureMap);
        }
    }

    @PutMapping(value = "/updateRate",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateRate(@RequestBody Rate rate) {
        try {
            if(null != rate) {
                Long id = rate.getRateId();
                if (rateManagementService
                    .getRateById(id)
                    .isPresent()) {
                    return ResponseEntity.ok(rateManagementService.saveRate(rate));
                } else {
                    log.error(RATEID_NOT_FOUND_MSG + id);
                    validationMap.put(MESSAGE, RATEID_NOT_FOUND_MSG);
                    return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(validationMap);
                }
            }else{
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(failureMap);
            }
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(failureMap);
        }
    }

    @DeleteMapping(value = "/deleteRate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteRate(@PathVariable Long id) {
        if (!rateManagementService.getRateById(id).isPresent()) {
            log.error(RATEID_NOT_FOUND_MSG + id);
            validationMap.put(MESSAGE, RATEID_NOT_FOUND_MSG);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validationMap);
        }
        rateManagementService.deleteRate(id);
        return ResponseEntity.ok().body(successMap);
    }

}
