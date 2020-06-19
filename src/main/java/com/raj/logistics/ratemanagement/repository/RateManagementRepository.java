package com.raj.logistics.ratemanagement.repository;

import com.raj.logistics.ratemanagement.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateManagementRepository extends JpaRepository<Rate, Long> {

}
